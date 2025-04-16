import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { guestApi } from "../../services/api";
import { useAuth } from "../../context/AuthContext";
import "../../styles/components.css";
import GuestForm from "./GuestForm";
//import GuestSearch from "./GuestSearch";
//import GuestSearchResults from "./GuestSearchResults";
import Modal from "../../components/common/Modal/Modal";

const GuestPage = () => {
  const [guests, setGuests] = useState([]);
  const [selectedGuest, setSelectedGuest] = useState(null);
  const [showGuestForm, setShowGuestForm] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { isAuthenticated, token, logout } = useAuth();
  const navigate = useNavigate();

  // Search state
  const [searchTerm, setSearchTerm] = useState("");
  const [searchType, setSearchType] = useState("name");
  const [searchResults, setSearchResults] = useState([]);

  useEffect(() => {
    if (!isAuthenticated || !token) {
      navigate("/login");
      return;
    }
    fetchGuests();
  }, [isAuthenticated, token, navigate]);

  const handleAuthError = async () => {
    await logout();
    navigate("/login");
  };

  const fetchGuests = async () => {
    try {
      setLoading(true);
      const response = await guestApi.getAllGuests();
      setGuests(response.data || []);
      setSearchResults(response.data || []);
      setError(null);
    } catch (err) {
      console.error("Error fetching guests:", err);
      if (err.response?.status === 401 || err.response?.status === 403) {
        await handleAuthError();
      } else {
        setError("Unable to load guests. Please try again later.");
      }
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchTerm.trim()) {
      setSearchResults(guests);
      return;
    }

    try {
      let result;
      console.log("Searching for:", searchTerm, "by type:", searchType);

      switch (searchType) {
        case "name":
          result = await guestApi.getGuestByName(searchTerm);
          console.log("Name search result:", result);
          break;
        case "email":
          result = await guestApi.getGuestByByEmail(searchTerm);
          console.log("Email search result:", result);
          break;
        default:
          result = [];
      }

      // Extract data from response if it exists
      if (result && result.data) {
        result = result.data;
      }

      // Ensure result is an array
      const searchResults = Array.isArray(result) ? result : [result];
      console.log("Final search results:", searchResults);

      setSearchResults(searchResults.filter((item) => item !== null));
    } catch (err) {
      console.error("Error searching guests:", err);
      setSearchResults([]);
    }
  };

  const handleAddGuest = () => {
    if (!token) {
      handleAuthError();
      return;
    }
    setSelectedGuest(null);
    setShowGuestForm(true);
  };

  const handleEditGuest = (guest) => {
    if (!token) {
      handleAuthError();
      return;
    }
    setSelectedGuest(guest);
    setShowGuestForm(true);
  };

  const handleDeleteGuest = async (guestId) => {
    if (!token) {
      handleAuthError();
      return;
    }

    if (window.confirm("Are you sure you want to delete this guest?")) {
      try {
        await guestApi.deleteGuest(guestId);
        setGuests(guests.filter((guest) => guest.id !== guestId));
        setSearchResults(searchResults.filter((guest) => guest.id !== guestId));
        setError(null);
      } catch (err) {
        console.error("Error deleting guest:", err);
        if (err.response?.status === 401 || err.response?.status === 403) {
          await handleAuthError();
        } else {
          setError("Failed to delete guest. Please try again later.");
        }
      }
    }
  };

  const handleGuestSubmit = async (guestData) => {
    if (!token) {
      handleAuthError();
      return;
    }

    try {
      if (selectedGuest) {
        // Update existing guest
        const response = await guestApi.updateGuest(
          selectedGuest.id,
          guestData
        );
        setGuests(
          guests.map((guest) =>
            guest.id === selectedGuest.id ? response.data : guest
          )
        );
        setSearchResults(
          searchResults.map((guest) =>
            guest.id === selectedGuest.id ? response.data : guest
          )
        );
      } else {
        // Create new guest
        const response = await guestApi.createGuest(guestData);
        setGuests([...guests, response.data]);
        setSearchResults([...searchResults, response.data]);
      }
      setShowGuestForm(false);
      setSelectedGuest(null);
      setError(null);
    } catch (err) {
      console.error("Error saving guest:", err);
      if (err.response?.status === 401 || err.response?.status === 403) {
        await handleAuthError();
      } else {
        setError("Failed to save guest. Please try again.");
      }
    }
  };

  if (!isAuthenticated || !token) {
    return null; // Will redirect in useEffect
  }

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner">Loading...</div>
      </div>
    );
  }

  return (
    <div className="container" style={{ padding: "2rem" }}>
      <div className="dashboard-header">
        <h2 className="dashboard-title">Guests</h2>
        <p className="dashboard-subtitle">Manage your guests</p>

{/*         <GuestSearch */}
{/*           searchTerm={searchTerm} */}
{/*           setSearchTerm={setSearchTerm} */}
{/*           searchType={searchType} */}
{/*           setSearchType={setSearchType} */}
{/*           onSearch={handleSearch} */}
{/*         /> */}

        <button
          className="button button-primary"
          onClick={handleAddGuest}
          style={{ marginTop: "1rem" }}
        >
          Add New Guest
        </button>
      </div>

      {error && (
        <div className="error-message" style={{ marginBottom: "1rem" }}>
          {error}
        </div>
      )}

{/*       <GuestSearchResults */}
{/*         guests={searchResults} */}
{/*         onEdit={handleEditGuest} */}
{/*         onDelete={handleDeleteGuest} */}
{/*       /> */}

      {showGuestForm && (
        <Modal onClose={() => setShowGuestForm(false)}>
          <GuestForm
            initialData={selectedGuest}
            onSubmit={handleGuestSubmit}
            onCancel={() => setShowGuestForm(false)}
          />
        </Modal>
      )}
    </div>
  );
};

export default GuestPage;
