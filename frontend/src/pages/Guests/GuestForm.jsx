import React, { useState, useEffect } from "react";
import { clientApi } from "../../services/api";
import "../../styles/components.css";
import styles from "./GuestForm.module.css";

const GuestForm = ({ initialData, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    name: "",
    emailAddress: "",
    rsvpStatus: "pending",
    notes: "",
  });

  const [errors, setErrors] = useState({});

  const validateForm = () => {
    const newErrors = {};
    if (!formData.name || formData.name.length < 3) {
      newErrors.name = "Name must be at least 3 characters long";
    }
    if (
      !formData.emailAddress ||
      !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.emailAddress)
    ) {
      newErrors.emailAddress = "Please enter a valid email address";
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     if (name === "phoneNumber") {
//       setFormData((prev) => ({
//         ...prev,
//         phoneNumber: {
//           phoneNumber: value,
//           isValid: validatePhoneNumber(value),
//         },
//       }));
//     } else {
//       setFormData((prev) => ({ ...prev, [name]: value }));
//     }
const handleChange = (e) => {
  const { name, value } = e.target;
  setFormData((prev) => ({ ...prev, [name]: value }));
    // Clear error when user starts typing
    if (errors[name]) {
      setErrors((prev) => ({ ...prev, [name]: undefined }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validateForm()) {
      try {
        // Submit the form data directly
        console.log("Submitting guest data:", formData);
        onSubmit(formData);
      } catch (error) {
        console.error("Error saving guest:", error);
        setErrors((prev) => ({
          ...prev,
          submit: "Failed to save guest. Please try again.",
        }));
      }
    }
  };

  return (
    <div className="form-container">
      <div className="form-header">
        <h2 className="form-title">
          {initialData ? "Edit Guest" : "Add New Guest"}
        </h2>
        <p className="form-subtitle">Enter the guest details below</p>
      </div>

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Guest Name</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className={`form-input ${errors.name ? "error" : ""}`}
            required
          />
          {errors.name && <div className="error-text">{errors.name}</div>}
        </div>

        <div className="form-group">
          <label className="form-label">Email Address</label>
          <input
            type="email"
            name="emailAddress"
            value={formData.emailAddress}
            onChange={handleChange}
            className={`form-input ${errors.emailAddress ? "error" : ""}`}
            required
          />
          {errors.emailAddress && (
            <div className="error-text">{errors.emailAddress}</div>
          )}
        </div>

        <div className="form-group">
          <label className="form-label">RSVP Status</label>
          <select
            name="rsvpStatus"
            value={formData.rsvpStatus}
            onChange={handleChange}
            className={`form-input ${errors.rsvpStatus ? "error" : ""}`}
            required
          >
            <option value="">Select RSVP Status</option>
            <option value="Confirmed">Confirmed</option>
            <option value="Pending">Pending</option>
            <option value="Declined">Declined</option>
          </select>
          {errors.rsvpStatus && (
            <div className="error-text">{errors.rsvpStatus}</div>
          )}
        </div>


        <div className="form-group">
          <label className="form-label">Notes</label>
          <textarea
            name="notes"
            value={formData.notes}
            onChange={handleChange}
            className="form-input"
            rows="4"
          />
        </div>

        {errors.submit && <div className="error-message">{errors.submit}</div>}

        <div className="flex" style={{ gap: "1rem", marginTop: "2rem" }}>
          <button type="submit" className="button button-primary">
            {initialData ? "Update Guest" : "Create Guest"}
          </button>
          <button
            type="button"
            onClick={onCancel}
            className="button button-secondary"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
};

export default GuestForm;
