import { useEffect, useState } from "react";
import api from "../api/api";
import { useParams } from "react-router-dom";
import Loader from "../components/Loader";

function Applicants() {

  const { jobId } = useParams();

  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchApplicants();
  }, [jobId]);

  const fetchApplicants = async () => {

    try {

      const res = await api.get(`/applications/job/${jobId}`);

      setApplications(res?.data?.data || []);

    } catch (error) {

      console.error("Error fetching applicants:", error);

    } finally {

      setLoading(false);

    }

  };

  const updateStatus = async (id, status) => {

    try {

      await api.put(`/applications/status/${id}?status=${status}`);

      fetchApplicants();

    } catch (error) {

      console.error("Status update failed", error);

    }

  };

  const getStatusColor = (status) => {

    if (status === "SHORTLISTED") return "bg-green-100 text-green-700";
    if (status === "REJECTED") return "bg-red-100 text-red-700";

    return "bg-blue-100 text-blue-700";
  };

  return (

    <div className="max-w-6xl mx-auto p-8">

      <h1 className="text-3xl font-bold mb-8 text-center">
        Applicants
      </h1>

      {loading && <Loader />}

      {!loading && applications.length === 0 && (

        <div className="text-center py-16">

          <h2 className="text-xl font-semibold text-gray-700">
            No Applicants Yet
          </h2>

          <p className="text-gray-500 mt-2">
            Candidates have not applied for this job.
          </p>

        </div>

      )}

      {!loading && applications.length > 0 && (

        <div className="grid md:grid-cols-2 gap-6">

          {applications.map((app) => (

            <div
              key={app.id}
              className="bg-white border rounded-xl p-6 shadow-sm hover:shadow-lg transition"
            >

              <div className="flex justify-between items-start">

                <div>

                  <h2 className="font-semibold text-lg">
                    {app.user?.name}
                  </h2>

                  <p className="text-gray-500 text-sm">
                    {app.user?.email}
                  </p>

                </div>

                <span className={`px-3 py-1 rounded text-sm ${getStatusColor(app.status)}`}>
                  {app.status}
                </span>

              </div>

              <div className="mt-4">

                <p className="text-sm text-gray-600">
                  Match Score:
                  <span className="ml-2 text-green-600 font-semibold">
                    {app.matchScore}%
                  </span>
                </p>

                <a
                  href={app.resumeUrl}
                  target="_blank"
                  rel="noreferrer"
                  className="text-blue-600 text-sm mt-2 inline-block"
                >
                  View Resume
                </a>

              </div>

              {app.status === "APPLIED" && (

                <div className="flex gap-3 mt-4">

                  <button
                    onClick={() => updateStatus(app.id, "SHORTLISTED")}
                    className="bg-green-600 text-white px-4 py-2 rounded text-sm hover:bg-green-700"
                  >
                    Shortlist
                  </button>

                  <button
                    onClick={() => updateStatus(app.id, "REJECTED")}
                    className="bg-red-600 text-white px-4 py-2 rounded text-sm hover:bg-red-700"
                  >
                    Reject
                  </button>

                </div>

              )}

            </div>

          ))}

        </div>

      )}

    </div>

  );

}

export default Applicants;