import { useEffect, useState } from "react";
import api from "../api/api";
import useAuth from "../hooks/useAuth";
import Loader from "../components/Loader";

function MyApplications() {
  const { userId } = useAuth();

  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (userId) {
      fetchApplications();
    }
  }, [userId]);

  const fetchApplications = async () => {
    try {
      const res = await api.get(`/applications/user/${userId}`);
      setApplications(res.data?.data || []);
    } catch (error) {
      console.error("Error fetching applications", error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <Loader />;

  return (
    <div className="max-w-5xl mx-auto px-6 py-10">
      <h1 className="text-3xl font-bold mb-6">My Applications</h1>

      {applications.length === 0 && (
        <p className="text-gray-500">You haven't applied for any jobs yet.</p>
      )}

      <div className="space-y-4">
        {applications.map((app) => (
          <div
            key={app.id}
            className="bg-white shadow-md rounded-lg p-5 border flex justify-between items-center"
          >
            <div>
              <h2 className="font-semibold text-lg">
                {app.job?.title}
              </h2>

              {/* FIX: company is an object */}
              <p className="text-gray-500 text-sm">
                {app.job?.company?.name}
              </p>
            </div>

            <span className="px-3 py-1 text-sm bg-blue-100 text-blue-700 rounded-full">
              {app.status}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyApplications;