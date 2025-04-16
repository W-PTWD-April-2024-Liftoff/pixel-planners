import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_URL || "http://localhost:8080";

// Configure axios defaults
const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Add a request interceptor to include the token
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Add a response interceptor to handle authentication errors
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const { status, data } = error.response;
      console.log("API Response Headers:", error.response.headers);
      console.log("Request URL:", error.config.url);
      console.log("Request Headers:", error.config.headers);

      if (status === 401 || status === 403) {
        // Clear token on auth errors
        console.error(`Authentication error (${status}):`, data);
        localStorage.removeItem("token");
      }
    } else if (error.request) {
      console.error("Network error:", error.message);
    }
    return Promise.reject(error);
  }
);

export const venueApi = {
  getAllVenues: () => axiosInstance.get("/api/venues/all"),
  getVenueById: (id) => axiosInstance.get(`/api/venues/find/${id}`),
  getVenueByName: (name) => axiosInstance.get(`/api/venues/find/name/${name}`),
  getVenueByLocation: (location) =>
  axiosInstance.get(`/api/venues/find/location/${location}`),
  getVenueByPhoneNumber: (phoneNumber) =>
  axiosInstance.get(`/api/venues/find/phone/${phoneNumber}`),
  getVenueByEmail: (emailAddress) =>
  axiosInstance.get(`/api/venues/find/email/${emailAddress}`),
  createVenue: (data) => axiosInstance.post("/api/venues/add", data),
  updateVenue: (id, data) => axiosInstance.put(`/api/venues/update/${id}`, data),
  deleteVenue: (id) => axiosInstance.delete(`/api/venues/delete/${id}`),
};

export const clientApi = {
  getAllClients: () => axiosInstance.get("/api/clients/all"),
  getClientById: (id) => axiosInstance.get(`/api/clients/find/${id}`),
  getClientByName: (name) => axiosInstance.get(`/api/clients/find/name/${name}`),
  getClientByPhoneNumber: (phoneNumber) =>
  axiosInstance.get(`/api/clients/find/phone/${phoneNumber}`),
  getClientByEmail: (emailAddress) =>
  axiosInstance.get(`/api/clients/find/email/${emailAddress}`),
  createClient: (data) => axiosInstance.post("/api/clients/add", data),
  updateClient: (id, data) => axiosInstance.put(`/api/clients/update/${id}`, data),
  deleteClient: (id) => axiosInstance.delete(`/api/clients/delete/${id}`),
};

export const guestApi = {
      getAllGuests: () => axiosInstance.get("/api/guests/all"),
      getGuestById: (id) => axiosInstance.get(`/api/guests/find/${id}`),
      getGuestByEmail: (email) => axiosInstance.get(`/api/guests/find/email/${email}`),
      getGuestsByGuestList: (guestListId) => axiosInstance.get(`/api/guests/find/guestListId/${guestListId}`),
      addGuest: (guestData) => axiosInstance.post("/api/guests/add", guestData),
      updateGuest: (id, guestData) => axiosInstance.put(`/api/guests/update/${id}`, guestData),
      deleteGuest: (id) => axiosInstance.delete(`/api/guests/delete/${id}`),
    };


//export const eventApi = {
//    getAllEvents:
//    getEventById:
//    createEvent:
//    updateEvent:
//    deleteEvent:
//    getPastEvents:
//    getUpcomingEvents:
//    rebookEvent:
//    getEventsByVenue:
//    getEventsByClient:
//    getEventsByVendor:
//};

export const eventApi = {
  getAllEvents: () => axiosInstance.get("/api/events/all"),
  getEventById: (id) => axiosInstance.get(`/api/events/find/${id}`),
  getEventByName: (name) => axiosInstance.get(`/api/events/find/name/${name}`),
  getEventsByVenue: (venueId) =>
    axiosInstance.get(`/api/events/find/venue/${venueId}`),
  getEventsByClient: (clientId) =>
    axiosInstance.get(`/api/events/find/client/${clientId}`),
  getEventsByVendor: (vendorId) =>
    axiosInstance.get(`/api/events/find/vendor/${vendorId}`),
  createEvent: (eventData) => axiosInstance.post("/api/events/add", eventData),
  updateEvent: (id, eventData) =>
    axiosInstance.put(`/api/events/update/${id}`, eventData),
  deleteEvent: (id) => axiosInstance.delete(`/api/events/delete/${id}`),
  rebookEvent: (id, rebookData) =>
    axiosInstance.post(`/api/events/rebook/${id}`, rebookData),
};

//export const vendorApi = {
//    getAllVendors:
//    getVendorById:
//    getVendorByName:
//    getVendorByLocation:
//    getVendorBySkills:
//    getVendorByPhoneNumber:
//    getVendorByEmail:
//    createVendor:
//    updateVendor:
//    deleteVendor:
//};

//export const guestApi = {
//    getAllGuests:
//    createGuest:
//    updateRsvp:
//    deleteGuest:
//};

//export const clientApi = {
//    getAllClients:
//    getClientById:
//    getClientByName:
//    getClientByLocation:
//    getClientByPhoneNumber:
//    getClientByEmail:
//    createClient:
//    updateClient:
//    deleteClient:
//};

//export const calendarApi = {
//    getCalendarByUser: (userId) =>
//       axiosInstance.get(`/api/calendars/user/${userId}`),
//    updateCalendar: (id, data) => axiosInstance.put(`/api/calendars/${id}`, data),
//    deleteCalendar: (id) => axiosInstance.delete(`/api/calendars/${id}`),
//};

export const calendarApi = {
  getMyCalendar: () => axiosInstance.get("/api/calendars/my"),
  getCalendarById: (id) => axiosInstance.get(`/api/calendars/${id}`),
  addCalendar: (calendarData) =>
    axiosInstance.post("/api/calendars", calendarData),
  deleteCalendar: (id) => axiosInstance.delete(`/api/calendars/${id}`),
};

export const authApi = {
  login: (credentials) => axiosInstance.post("/api/auth/login", credentials),
  register: (userData) => axiosInstance.post("/api/auth/register", userData),
  getCurrentUser: () => axiosInstance.get("/api/auth/user"),
  logout: () => axiosInstance.post("/api/auth/logout"),
};
