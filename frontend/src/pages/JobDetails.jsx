import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api/api";
import Loader from "../components/Loader";
import useAuth from "../hooks/useAuth";

function JobDetails() {

  const { id } = useParams();
  const { userId } = useAuth();

  const [job, setJob] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchJob();
  }, [id]);

  const fetchJob = async () => {

    try {

      const res = await api.get(`/jobs/${id}`);

      setJob(res.data?.data);

    } catch (error) {

      console.error("Error fetching job:", error);

    } finally {

      setLoading(false);

    }

  };

  const applyJob = async () => {

    if (!userId) {
      alert("Please login first");
      return;
    }

    try {

      await api.post("/applications/apply", {
        jobId: id,
        userId: userId
      });

      alert("Application submitted successfully");

    } catch (error) {

      console.error("Error applying job:", error);
      alert("Application failed");

    }

  };

  if (loading) return <Loader />;

  if (!job)
    return (
      <p className="text-center text-gray-500 mt-10">
        Job not found
      </p>
    );

  return (

    <div className="max-w-4xl mx-auto px-6 py-10">

      {/* Job Header */}
      <div className="bg-white shadow-md rounded-xl p-8 mb-6">

        <h1 className="text-3xl font-bold text-gray-800 mb-2">
          {job.title}
        </h1>

        <p className="text-gray-600 mb-1">
          <span className="font-semibold">Company:</span>{" "}
          {job.company?.name || "N/A"}
        </p>

        <p className="text-gray-600 mb-1">
          <span className="font-semibold">Location:</span>{" "}
          {job.location || job.company?.location}
        </p>

        <p className="text-green-600 font-semibold text-lg mt-2">
          Salary: ₹{job.salary}
        </p>

      </div>

      {/* Skills */}
      <div className="bg-white shadow-md rounded-xl p-6 mb-6">

        <h2 className="text-xl font-semibold mb-4">
          Required Skills
        </h2>

        <div className="flex flex-wrap gap-3">

          {(job.skills || "").split(",").map((skill, index) => (
            <span
              key={index}
              className="bg-blue-100 text-blue-700 px-3 py-1 rounded-full text-sm"
            >
              {skill.trim()}
            </span>
          ))}

        </div>

      </div>

      {/* Description */}
      <div className="bg-white shadow-md rounded-xl p-6 mb-6">

        <h2 className="text-xl font-semibold mb-4">
          Job Description
        </h2>

        <p className="text-gray-700 leading-relaxed">
          {job.description}
        </p>

      </div>

      {/* Apply Button */}
      <div className="flex justify-center">

        <button
          onClick={applyJob}
          className="bg-blue-600 text-white px-8 py-3 rounded-lg text-lg hover:bg-blue-700 transition shadow"
        >
          Apply Now
        </button>

      </div>

    </div>

  );

}

export default JobDetails;