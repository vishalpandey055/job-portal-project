import { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

  const navigate = useNavigate();

  const [token, setToken] = useState(null);
  const [userId, setUserId] = useState(null);
  const [role, setRole] = useState(null);

  useEffect(() => {

    const storedToken = localStorage.getItem("token");
    const storedUserId = localStorage.getItem("userId");
    const storedRole = localStorage.getItem("role");

    if (storedToken) {
      setToken(storedToken);
      setUserId(storedUserId);
      setRole(storedRole);
    }

  }, []);

  const login = (jwt, id, userRole) => {

    // store in localStorage
    localStorage.setItem("token", jwt);
    localStorage.setItem("userId", id);
    localStorage.setItem("role", userRole);

    // store in state
    setToken(jwt);
    setUserId(id);
    setRole(userRole);

    // redirect based on role
    if (userRole === "RECRUITER") {
      navigate("/recruiter/dashboard");
    } else {
      navigate("/candidate/dashboard");
    }

  };

  const logout = () => {

    localStorage.clear();

    setToken(null);
    setUserId(null);
    setRole(null);

    navigate("/login");

  };

  const isAuthenticated = !!token;

  return (

    <AuthContext.Provider
      value={{
        token,
        userId,
        role,
        login,
        logout,
        isAuthenticated
      }}
    >

      {children}

    </AuthContext.Provider>

  );

};