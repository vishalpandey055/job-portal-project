import { Routes, Route, Navigate } from "react-router-dom";

import Login from "../pages/Login";
import Register from "../pages/Register";
import Jobs from "../pages/Jobs";
import Dashboard from "../pages/Dashboard";
import JobDetails from "../pages/JobDetails";
import AdminDashboard from "../pages/AdminDashboard";
import RecruiterDashboard from "../pages/RecruiterDashboard";
import MyApplications from "../pages/MyApplications";
import PostJob from "../pages/PostJob";
import RecruiterJobs from "../pages/RecruiterJobs";
import Applicants from "../pages/Applicants";

import ProtectedRoute from "./ProtectedRoute";

function AppRoutes() {

  return (

    <Routes>

      {/* Default redirect */}
      <Route path="/" element={<Navigate to="/jobs" replace />} />

      {/* Public Routes */}
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      {/* Jobs */}
      <Route
        path="/jobs"
        element={
          <ProtectedRoute>
            <Jobs />
          </ProtectedRoute>
        }
      />

      {/* Job Details */}
      <Route
        path="/jobs/:id"
        element={
          <ProtectedRoute>
            <JobDetails />
          </ProtectedRoute>
        }
      />

      {/* Candidate Dashboard */}
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute role="CANDIDATE">
            <Dashboard />
          </ProtectedRoute>
        }
      />

      {/* Candidate Applications */}
      <Route
        path="/applications"
        element={
          <ProtectedRoute role="CANDIDATE">
            <MyApplications />
          </ProtectedRoute>
        }
      />

      {/* Recruiter Routes */}
      <Route
        path="/recruiter"
        element={<Navigate to="/recruiter/dashboard" replace />}
      />

      <Route
        path="/recruiter/dashboard"
        element={
          <ProtectedRoute role="RECRUITER">
            <RecruiterDashboard />
          </ProtectedRoute>
        }
      />

      <Route
        path="/recruiter/post-job"
        element={
          <ProtectedRoute role="RECRUITER">
            <PostJob />
          </ProtectedRoute>
        }
      />

      <Route
        path="/recruiter/jobs"
        element={
          <ProtectedRoute role="RECRUITER">
            <RecruiterJobs />
          </ProtectedRoute>
        }
      />

      <Route
        path="/recruiter/applicants/:jobId"
        element={
          <ProtectedRoute role="RECRUITER">
            <Applicants />
          </ProtectedRoute>
        }
      />

      {/* ADMIN REDIRECT FIX */}
      <Route
        path="/admin"
        element={<Navigate to="/admin/dashboard" replace />}
      />

      {/* Admin Dashboard */}
      <Route
        path="/admin/dashboard"
        element={
          <ProtectedRoute role="ADMIN">
            <AdminDashboard />
          </ProtectedRoute>
        }
      />

      {/* 404 Page */}
      <Route
        path="*"
        element={
          <div className="flex items-center justify-center h-[60vh] text-gray-500 text-lg">
            404 | Page Not Found
          </div>
        }
      />

    </Routes>

  );
}

export default AppRoutes;