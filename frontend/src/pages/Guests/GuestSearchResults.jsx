import React from "react";
import "../../styles/components.css";

const GuestSearchResults = ({ guests, onEdit, onDelete }) => {
  if (guests.length === 0) {
    return (
      <div className="empty-state">
        <p>No guests found. Try adjusting your search or add a new guest!</p>
      </div>
    );
  }

  return (
    <div className="grid-container">
      {guests.map((guest) => (
        <div key={guest.id} className="guest-card">
          <div className="guest-header">
            <h3 className="guest-title">{guest.name}</h3>
            <div className="flex" style={{ gap: "0.5rem" }}>
              <button
                className="button button-outline"
                onClick={() => onEdit(guest)}
              >
                Edit
              </button>
              <button
                className="button button-secondary"
                onClick={() => onDelete(guest.id)}
              >
                Delete
              </button>
            </div>
          </div>
          <div className="guest-details">
            <p>📧 {guest.emailAddress}</p>
            <p>📋 RSVP: {guest.rsvpStatus}</p>
            {guest.notes && <p>📝 {guest.notes}</p>}
          </div>
        </div>
      ))}
    </div>
  );
};

export default GuestSearchResults;
