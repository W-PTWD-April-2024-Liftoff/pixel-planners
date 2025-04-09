//import axios from "axios";
//
//const API_URL = "http://localhost:8080/api/user";
//
//// Retrieve all users from the database
//export const fetchUsers = async () => {
//  try {
//    const response = await axios.get(`${API_URL}/all`, {
//      withCredentials: true,
//    });
//    return response.data;
//  } catch (error) {
//    console.error("There was an error fetching all users!", error);
//    throw error;
//  }
//};
//
//// Fetch the current user's profile
//export const getUserProfile = async () => {
//  try {
//    const response = await axios.get(`${API_URL}/profile`, {
//      withCredentials: true,
//    });
//    return response.data;
//  } catch (error) {
//    console.error("Error fetching user profile!", error);
//    throw error;
//  }
//};
//
//// Update the user's profile
//export const updateUser = async (id, name, emailAddress, pictureUrl) => {
//  const userData = {
//    id,
//    name,
//    emailAddress,
//    pictureUrl,
//  };
//
//  try {
//    const response = await axios.patch(`${API_URL}/update/${id}`, userData, {
//      headers: { "Content-Type": "application/json" },
//      withCredentials: true,
//    });
//    console.log("Update Response: ", response.data, response.status);
//    return response.data;
//  } catch (error) {
//    console.error("Error updating user profile!", error);
//    throw error;
//  }
//};
//
//// Delete a user by ID
//export const deleteUser = async (userId) => {
//  try {
//    await axios.post(`${API_URL}/delete`, null, {
//      params: { userId },
//      withCredentials: true,
//    });
//  } catch (error) {
//    console.error("There was an error deleting the user!", error);
//    throw error;
//  }
//};
//
//// Reset the user's password
//export const resetPassword = async (username, newPassword, verifyPassword) => {
//  const resetData = {
//    username,
//    newPassword,
//    verifyPassword,
//  };
//
//  try {
//    const response = await axios.post(`${API_URL}/reset-password`, resetData, {
//      headers: { "Content-Type": "application/json" },
//      withCredentials: true,
//    });
//    console.log("Password Reset Response: ", response.data, response.status);
//    return response.data;
//  } catch (error) {
//    console.error("Error resetting password!", error);
//    throw error;
//  }
//};