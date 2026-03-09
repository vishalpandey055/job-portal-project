import { useEffect, useState } from "react";
import api from "../api/api";
import { Link } from "react-router-dom";

function RecruiterJobs() {

  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {

    try {

      const res = await api.get("/jobs/recruiter");

      setJobs(res.data.data);

    } catch (err) {

      console.error(err);

    }

  };

  return (

    <div className="max-w-6xl mx-auto p-8">

      <h1 className="text-3xl font-bold mb-6">
        My Posted Jobs
      </h1>

      {jobs.length === 0 && (
        <p className="text-gray-500">
          No jobs posted yet
        </p>
      )}

      {jobs.map(job => (

        <div
          key={job.id}
          className="border p-4 mb-4 rounded flex justify-between items-center"
        >

          <div>

            <h2 className="font-semibold text-lg">
              {job.title}
            </h2>

            <p className="text-gray-500 text-sm">
              {job.location}
            </p>

          </div>

          <Link
            to={`/recruiter/applicants/${job.id}`}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700"
          >
            View Applicants
          </Link>

        </div>

      ))}

    </div>

  );

}

export default RecruiterJobs;