import { useEffect, useState } from "react";
import api from "../api/api";
import JobCard from "../components/JobCard";
import Loader from "../components/Loader";

function Jobs() {

  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  const [keyword, setKeyword] = useState("");
  const [searchType, setSearchType] = useState("title");

  useEffect(() => {
    fetchJobs();
  }, [page]);

  const fetchJobs = async () => {

    setLoading(true);

    try {

      const res = await api.get(`/jobs?page=${page}&size=6`);

      const pageData = res?.data?.data;

      if (pageData) {
        setJobs(pageData.content || []);
        setTotalPages(pageData.totalPages || 1);
      } else {
        setJobs([]);
        setTotalPages(1);
      }

    } catch (error) {

      console.error("Error fetching jobs:", error);
      setJobs([]);

    } finally {

      setLoading(false);

    }

  };

  const searchJobs = async () => {

    if (!keyword.trim()) {
      fetchJobs();
      return;
    }

    setLoading(true);

    try {

      const res = await api.get(
        `/jobs/search/${searchType}?${searchType}=${keyword}`
      );

      setJobs(res?.data?.data || []);
      setTotalPages(1);

    } catch (err) {

      console.error("Search error", err);
      setJobs([]);

    } finally {

      setLoading(false);

    }

  };

  const clearSearch = () => {
    setKeyword("");
    setPage(0);
    fetchJobs();
  };

  return (

    <div className="min-h-screen bg-gray-50 py-10">

      <div className="max-w-7xl mx-auto px-6">

        {/* Header */}
        <div className="mb-10 text-center">

          <h1 className="text-4xl font-bold text-gray-800 mb-3">
            Find Your Dream Job
          </h1>

          <p className="text-gray-500">
            Browse the latest job opportunities
          </p>

        </div>

        {/* Search Bar */}
        <div className="flex justify-center mb-10">

          <div className="flex gap-3 w-full max-w-3xl">

            <input
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && searchJobs()}
              placeholder="Search jobs..."
              className="flex-1 border px-4 py-3 rounded-lg focus:ring-2 focus:ring-blue-500 outline-none"
            />

            <select
              value={searchType}
              onChange={(e) => setSearchType(e.target.value)}
              className="border px-4 py-3 rounded-lg bg-white"
            >
              <option value="title">Title</option>
              <option value="location">Location</option>
              <option value="skill">Skill</option>
            </select>

            <button
              onClick={searchJobs}
              className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700"
            >
              Search
            </button>

            {keyword && (
              <button
                onClick={clearSearch}
                className="px-4 py-3 border rounded-lg bg-white hover:bg-gray-100"
              >
                Clear
              </button>
            )}

          </div>

        </div>

        {/* Job Count */}
        {!loading && jobs.length > 0 && (
          <p className="text-gray-500 mb-6 text-sm">
            {jobs.length} jobs found
          </p>
        )}

        {loading && <Loader />}

        {/* Empty State */}
        {!loading && jobs.length === 0 && (

          <div className="text-center py-20">

            <div className="text-6xl mb-4">🔎</div>

            <h3 className="text-2xl font-semibold text-gray-700">
              No Jobs Found
            </h3>

            <p className="text-gray-500 mt-2 mb-6">
              We couldn't find jobs matching your search
            </p>

            <button
              onClick={clearSearch}
              className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700"
            >
              View All Jobs
            </button>

          </div>

        )}

        {/* Jobs Grid */}
        {!loading && jobs.length > 0 && (

          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">

            {jobs.map((job) => (
              <JobCard key={job.id} job={job} />
            ))}

          </div>

        )}

        {/* Pagination */}
        {keyword === "" && (

          <div className="flex justify-center items-center gap-6 mt-12">

            <button
              disabled={page === 0}
              onClick={() => setPage((prev) => prev - 1)}
              className="px-5 py-2 bg-white border rounded-lg shadow hover:bg-gray-100 disabled:opacity-50"
            >
              ← Previous
            </button>

            <span className="font-semibold text-gray-700">
              Page {page + 1} / {totalPages}
            </span>

            <button
              disabled={page + 1 >= totalPages}
              onClick={() => setPage((prev) => prev + 1)}
              className="px-5 py-2 bg-white border rounded-lg shadow hover:bg-gray-100 disabled:opacity-50"
            >
              Next →
            </button>

          </div>

        )}

      </div>

    </div>

  );

}

export default Jobs;