import React from "react";
import "../../styles/components.css";

const ClientSearchResults = ({ clients, onEdit, onDelete }) => {
  if (clients.length === 0) {
    return (
      <div className="empty-state">
        <p>No clients found. Try adjusting your search or add a new client!</p>
      </div>
    );
  }

  return (
    <div className="grid-container">
      {clients.map((client) => (
        <div key={client.id} className="client-card">
          <div className="client-header">
            <h3 className="client-title">{client.name}</h3>
            <div className="flex" style={{ gap: "0.5rem" }}>
              <button
                className="button button-outline"
                onClick={() => onEdit(client)}
              >
                Edit
              </button>
              <button
                className="button button-secondary"
                onClick={() => onDelete(client.id)}
              >
                Delete
              </button>
            </div>
          </div>
          <div className="client-details">
            <p>📧 {client.emailAddress}</p>
            <p>
              📞{" "}
              {typeof client.phoneNumber === "object"
                ? client.phoneNumber.phoneNumber
                : client.phoneNumber}
            </p>
            {client.notes && <p>📝 {client.notes}</p>}
          </div>
        </div>
      ))}
    </div>
  );
};

export default ClientSearchResults;
