import { Link } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import NotificationBell from "./NotificationBell";

function Navbar() {

  const { token, logout, role } = useContext(AuthContext);

  return (

    <nav className="bg-white border-b shadow-sm sticky top-0 z-50">

      <div className="max-w-7xl mx-auto px-6 py-4 flex justify-between items-center">

        {/* Logo */}
        <Link
          to="/"
          className="flex items-center gap-2 text-xl font-bold text-blue-600"
        >
          <span className="text-2xl">💼</span>
          JobPortal
        </Link>

        {/* Navigation */}
        <div className="flex items-center gap-6 text-sm font-medium">

          {/* Not Logged In */}
          {!token && (
            <>

              <Link
                to="/login"
                className="text-gray-600 hover:text-blue-600"
              >
                Login
              </Link>

              <Link
                to="/register"
                className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700"
              >
                Register
              </Link>

            </>
          )}

          {/* Logged In */}
          {token && (
            <>

              <Link
                to="/jobs"
                className="text-gray-600 hover:text-blue-600"
              >
                Jobs
              </Link>

              {/* Candidate */}
              {role === "CANDIDATE" && (
                <>
                  <Link
                    to="/dashboard"
                    className="text-gray-600 hover:text-blue-600"
                  >
                    Dashboard
                  </Link>

                  <Link
                    to="/applications"
                    className="text-gray-600 hover:text-blue-600"
                  >
                    My Applications
                  </Link>
                </>
              )}

              {/* Recruiter */}
              {role === "RECRUITER" && (
                <Link
                  to="/recruiter"
                  className="text-gray-600 hover:text-blue-600"
                >
                  Recruiter Panel
                </Link>
              )}

              {/* Admin */}
              {role === "ADMIN" && (
                <Link
                  to="/admin"
                  className="text-gray-600 hover:text-blue-600"
                >
                  Admin Panel
                </Link>
              )}

              {/* Notifications */}
              <NotificationBell />

              {/* Logout */}
              <button
                onClick={logout}
                className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600"
              >
                Logout
              </button>

            </>
          )}

        </div>

      </div>

    </nav>

  );

}

export default Navbar;