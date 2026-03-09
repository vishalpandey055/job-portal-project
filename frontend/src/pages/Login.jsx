import { useState } from "react";
import api from "../api/api";
import useAuth from "../hooks/useAuth";
import { Link, useNavigate } from "react-router-dom";

function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
    role: "CANDIDATE",
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!form.email.trim() || !form.password.trim()) {
      alert("Please enter email and password");
      return;
    }

    setLoading(true);

    try {
      const res = await api.post("/auth/login", form);

      const data = res.data?.data;

      if (!data?.accessToken || !data?.id) {
        alert("Invalid server response");
        return;
      }

      login(data.accessToken, data.id, data.role);

      // Redirect based on role
      switch (data.role) {
        case "ADMIN":
          navigate("/admin/dashboard");
          break;
        case "RECRUITER":
          navigate("/recruiter/dashboard");
          break;
        default:
          navigate("/dashboard");
      }
    } catch (err) {
      console.error(err);
      alert(err.response?.data?.message || "Invalid credentials");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-br from-blue-100 via-blue-200 to-blue-300 px-4">
      <div className="w-full max-w-md bg-white/90 backdrop-blur-sm shadow-2xl rounded-2xl p-10">
        <h2 className="text-3xl font-bold text-center text-gray-800">
          Welcome Back
        </h2>

        <p className="text-center text-gray-500 mt-2 mb-8">
          Login to your JobPortal account
        </p>

        <form onSubmit={handleSubmit} className="space-y-5">
          {/* Email */}
          <div>
            <input
              type="email"
              name="email"
              placeholder="Email address"
              required
              value={form.email}
              onChange={handleChange}
              className="w-full border border-gray-300 px-4 py-3 rounded-lg
          focus:ring-2 focus:ring-blue-500 focus:border-blue-500
          outline-none transition"
            />
          </div>

          {/* Password */}
          <div>
            <input
              type="password"
              name="password"
              placeholder="Password"
              required
              value={form.password}
              onChange={handleChange}
              className="w-full border border-gray-300 px-4 py-3 rounded-lg
          focus:ring-2 focus:ring-blue-500 focus:border-blue-500
          outline-none transition"
            />
          </div>

          {/* Role Selection */}
          <div>
            <label className="block text-sm font-semibold text-gray-600 mb-2">
              Select Role
            </label>

            <div className="relative">
              <select
                name="role"
                value={form.role}
                onChange={handleChange}
                className="appearance-none w-full px-4 py-3 pr-10
            border border-gray-300 rounded-lg
            bg-white text-gray-700 text-sm
            shadow-sm
            focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500
            hover:border-gray-400
            transition duration-200 cursor-pointer"
              >
                <option value="CANDIDATE">Candidate</option>
                <option value="RECRUITER">Recruiter</option>
                <option value="ADMIN">Admin</option>
              </select>

              {/* Dropdown Arrow */}
              <div className="pointer-events-none absolute inset-y-0 right-3 flex items-center text-gray-500">
                <svg
                  className="w-4 h-4"
                  fill="none"
                  stroke="currentColor"
                  strokeWidth="2"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M19 9l-7 7-7-7"
                  />
                </svg>
              </div>
            </div>
          </div>

          {/* Button */}
          <button
            disabled={loading}
            className="w-full bg-blue-600 text-white py-3 rounded-lg font-semibold
        hover:bg-blue-700 active:scale-95 transition transform
        disabled:opacity-50"
          >
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>

        <p className="text-center text-gray-500 text-sm mt-6">
          Don't have an account?{" "}
          <Link
            to="/register"
            className="text-blue-600 font-semibold hover:underline"
          >
            Register
          </Link>
        </p>
      </div>
    </div>
  );
}

export default Login;
