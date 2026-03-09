import { useState } from "react";
import api from "../api/api";

function PostJob() {

  const [form, setForm] = useState({
    title: "",
    description: "",
    companyId: "",
    location: "",
    skills: "",
    salary: ""
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const createJob = async (e) => {

    e.preventDefault();

    // basic validation
    if (!form.title || !form.description || !form.companyId || !form.location || !form.salary) {
      alert("Please fill all required fields");
      return;
    }

    setLoading(true);

    try {

      const payload = {
        title: form.title.trim(),
        description: form.description.trim(),
        companyId: parseInt(form.companyId),
        location: form.location.trim(),
        skills: form.skills.trim(),
        salary: parseFloat(form.salary)
      };

      await api.post("/jobs", payload);

      alert("Job Created Successfully");

      setForm({
        title: "",
        description: "",
        companyId: "",
        location: "",
        skills: "",
        salary: ""
      });

    } catch (err) {

      console.error(err);

      alert(
        err.response?.data?.message || "Failed to create job"
      );

    } finally {
      setLoading(false);
    }

  };

  return (

    <div className="max-w-4xl mx-auto px-6 py-10">

      <h1 className="text-3xl font-bold mb-8">
        Post New Job
      </h1>

      <div className="bg-white shadow-md rounded-xl p-8">

        <form onSubmit={createJob} className="space-y-6">

          <input
            name="title"
            placeholder="Job Title"
            required
            value={form.title}
            onChange={handleChange}
            className="border p-3 w-full rounded-lg"
          />

          <textarea
            name="description"
            placeholder="Job Description"
            required
            value={form.description}
            onChange={handleChange}
            rows="4"
            className="border p-3 w-full rounded-lg"
          />

          <input
            name="companyId"
            type="number"
            placeholder="Company ID"
            required
            value={form.companyId}
            onChange={handleChange}
            className="border p-3 w-full rounded-lg"
          />

          <input
            name="location"
            placeholder="Location"
            required
            value={form.location}
            onChange={handleChange}
            className="border p-3 w-full rounded-lg"
          />

          <input
            name="skills"
            placeholder="Required Skills"
            value={form.skills}
            onChange={handleChange}
            className="border p-3 w-full rounded-lg"
          />

          <input
            name="salary"
            type="number"
            placeholder="Salary"
            required
            value={form.salary}
            onChange={handleChange}
            className="border p-3 w-full rounded-lg"
          />

          <button
            disabled={loading}
            className="bg-blue-600 text-white px-6 py-3 rounded-lg w-full"
          >
            {loading ? "Posting..." : "Post Job"}
          </button>

        </form>

      </div>

    </div>

  );

}

export default PostJob;