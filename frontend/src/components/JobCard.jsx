import { Link } from "react-router-dom";
import api from "../api/api";
import useAuth from "../hooks/useAuth";

function JobCard({ job }) {

  const { token } = useAuth();

  const applyJob = async (jobId) => {

    if (!token) {
      alert("Please login first");
      return;
    }

    try {

      await api.post("/applications/apply", {
        jobId: jobId,
        resumeUrl: "default-resume.pdf"
      });

      alert("Applied Successfully");

    } catch (error) {

      console.error(error);

      alert(
        error.response?.data?.message ||
        "Application Failed"
      );

    }

  };

  return (

    <div className="bg-white rounded-xl shadow-sm hover:shadow-xl hover:-translate-y-1 transition duration-300 border border-gray-200 p-6 flex flex-col justify-between">

      <div>

        {/* Company */}
        <div className="flex items-center gap-3 mb-4">

          <div className="w-10 h-10 bg-blue-100 text-blue-600 flex items-center justify-center rounded-full font-bold">
            {job.company?.name?.charAt(0) || "J"}
          </div>

          <div>

            <p className="text-sm font-semibold text-gray-700">
              {job.company?.name || "Company"}
            </p>

            <p className="text-xs text-gray-500">
              📍 {job.location}
            </p>

          </div>

        </div>

        {/* Title */}
        <Link to={`/jobs/${job.id}`}>

          <h2 className="text-lg font-semibold text-gray-800 hover:text-blue-600 flex items-center gap-2">
            💼 {job.title}
          </h2>

        </Link>

        {/* Skills */}
        <div className="flex flex-wrap gap-2 mt-3">

          {job.skills?.split(",").slice(0,3).map((skill,index)=>(

            <span
              key={index}
              className="bg-gray-100 text-gray-700 text-xs px-3 py-1 rounded-full"
            >
              {skill.trim()}
            </span>

          ))}

        </div>

      </div>

      {/* Footer */}
      <div className="mt-6 flex justify-between items-center">

        <span className="text-green-600 font-semibold text-sm">
          ₹{job.salary}
        </span>

        <div className="flex gap-3">

          <Link
            to={`/jobs/${job.id}`}
            className="text-sm text-gray-600 hover:text-blue-600"
          >
            Details
          </Link>

          <button
            onClick={() => applyJob(job.id)}
            className="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-700"
          >
            Apply
          </button>

        </div>

      </div>

    </div>

  );

}

export default JobCard;