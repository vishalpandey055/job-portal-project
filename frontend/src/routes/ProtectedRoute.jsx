import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

function ProtectedRoute({ children, role }) {
  const { token, role: userRole } = useContext(AuthContext);

  // If not logged in → go to login
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // If role restriction exists and user role doesn't match
  if (role && role !== userRole) {
    // Redirect user based on their role
    if (userRole === "RECRUITER") {
      return <Navigate to="/recruiter/dashboard" replace />;
    }

    if (userRole === "ADMIN") {
      return <Navigate to="/admin/dashboard" replace />;
    }

    // Default → candidate
    return <Navigate to="/dashboard" replace />;
  }

  return children;
}

export default ProtectedRoute;
