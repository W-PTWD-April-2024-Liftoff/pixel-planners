import React, { useState, useEffect } from "react";
import { useAuth } from "../../context/AuthContext";
import Calendar from "./Calendar";
import EventForm from "./EventForm";
import UpcomingEvents from "../../components/UpcomingEvents/UpcomingEvents";
import { eventApi } from "../../services/api";
import "../../styles/components.css";

const Dashboard = () => {
  const { user, logout, token } = useAuth();
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showEventForm, setShowEventForm] = useState(false);
  const [successMessage, setSuccessMessage] = useState(null);

  useEffect(() => {
    if (token) {
      fetchEvents();
    }
  }, [token]);

  const fetchEvents = async () => {
    try {
      setLoading(true);
      const response = await eventApi.getAllEvents();
      setEvents(response.data);
    } catch (err) {
      console.error("Error fetching events:", err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleAddEvent = () => {
    setShowEventForm(true);
  };

  const handleSaveEvent = async () => {
    try {
      setError(null);
      setSuccessMessage(null);
      await fetchEvents();
      setSuccessMessage("Event created successfully!");
      setShowEventForm(false);
    } catch (err) {
      console.error("Error saving event:", err);
      setError("Failed to create event. Please try again.");
    }
  };

  const handleCancelEvent = () => {
    setShowEventForm(false);
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner">Loading...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="error-container">
        <div className="error-message">
          <h3>Error Loading Events</h3>
          <p>{error}</p>
          <button onClick={fetchEvents} className="button button-primary">
            Try Again
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="dashboard-container">
      <nav className="card">
        <div className="container">
          <div
            className="flex"
            style={{
              justifyContent: "space-between",
              alignItems: "center",
              height: "4rem",
            }}
          >
            <div className="flex" style={{ alignItems: "center" }}>
              <h1 className="card-title">Event Vista</h1>
            </div>
            <div className="flex" style={{ alignItems: "center", gap: "1rem" }}>
              <span className="card-content">
                Welcome, {user?.name || "User"}
              </span>
              <button
                onClick={handleAddEvent}
                className="button button-primary"
              >
                Add Event
              </button>
              <button onClick={logout} className="button button-secondary">
                Logout
              </button>
            </div>
          </div>
        </div>
      </nav>

      <div className="dashboard-content">
        {successMessage && (
          <div className="success-message" style={{ margin: "1rem 0" }}>
            {successMessage}
          </div>
        )}
        {error && (
          <div className="error-message" style={{ margin: "1rem 0" }}>
            {error}
          </div>
        )}
        <Calendar events={events} />
        <UpcomingEvents events={events} />
      </div>

      {showEventForm && (
        <div className="modal-overlay">
          <div className="modal-container">
            <EventForm
              onSubmit={handleSaveEvent}
              onCancel={handleCancelEvent}
            />
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;

