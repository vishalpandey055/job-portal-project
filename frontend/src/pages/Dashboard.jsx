import { useEffect, useState } from "react";
import api from "../api/api";
import useAuth from "../hooks/useAuth";
import Loader from "../components/Loader";
import { useNavigate } from "react-router-dom";

function Dashboard() {

  const { userId } = useAuth();
  const navigate = useNavigate();

  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {

    if (!userId) {
      navigate("/login");
      return;
    }

    fetchApplications();

  }, [userId, navigate]);

  const fetchApplications = async () => {

    try {

      const res = await api.get(`/applications/user/${userId}`);

      const data = res?.data?.data || res?.data || [];

      setApplications(data);

    } catch (err) {

      console.error("Error fetching applications:", err);
      setApplications([]);

    } finally {

      setLoading(false);

    }

  };

  const getStatusColor = (status) => {

    switch (status) {

      case "APPLIED":
        return "bg-blue-100 text-blue-700";

      case "SHORTLISTED":
        return "bg-green-100 text-green-700";

      case "REJECTED":
        return "bg-red-100 text-red-700";

      default:
        return "bg-gray-100 text-gray-700";

    }

  };

  if (loading) return <Loader />;

  return (

    <div className="max-w-6xl mx-auto px-6 py-10">

      <h1 className="text-3xl font-bold mb-8">
        My Applications
      </h1>

      {applications.length === 0 ? (

        <p className="text-gray-500">
          You haven't applied for any jobs yet.
        </p>

      ) : (

        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">

          {applications.map((app) => (

            <div
              key={app.id}
              className="bg-white shadow-md rounded-xl p-6 hover:shadow-xl transition"
            >

              <h2 className="text-xl font-semibold text-gray-800 mb-2">
                {app.job?.title || "Job Title"}
              </h2>

              <p className="text-gray-600 mb-1">
                {app.job?.company?.name || "Company"}
              </p>

              <p className="text-sm text-gray-500">
                📍 {app.job?.company?.location || "Location"}
              </p>

              <div className="flex justify-between items-center mt-4">

                <span
                  className={`text-xs px-3 py-1 rounded-full ${getStatusColor(app.status)}`}
                >
                  {app.status}
                </span>

                <span className="text-sm font-semibold text-green-600">
                  {app.matchScore || 0}% match
                </span>

              </div>

            </div>

          ))}

        </div>

      )}

    </div>

  );

}

export default Dashboard;