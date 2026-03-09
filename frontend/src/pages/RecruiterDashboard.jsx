import { Link } from "react-router-dom";

function RecruiterDashboard() {

  return (

    <div className="max-w-6xl mx-auto p-8">

      <h1 className="text-3xl font-bold mb-6">
        Recruiter Dashboard
      </h1>

      <p className="text-gray-500 mb-8">
        Manage jobs and applicants
      </p>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

        <Link
          to="/recruiter/post-job"
          className="bg-blue-600 text-white p-6 rounded-xl shadow hover:bg-blue-700 transition"
        >
          <h2 className="text-xl font-semibold">
            Post New Job
          </h2>
          <p className="text-sm opacity-90 mt-2">
            Create a new job opening
          </p>
        </Link>

        <Link
          to="/recruiter/jobs"
          className="bg-green-600 text-white p-6 rounded-xl shadow hover:bg-green-700 transition"
        >
          <h2 className="text-xl font-semibold">
            My Jobs
          </h2>
          <p className="text-sm opacity-90 mt-2">
            View and manage posted jobs
          </p>
        </Link>

      </div>

    </div>

  );

}

export default RecruiterDashboard;