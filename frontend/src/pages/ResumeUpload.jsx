import { useState } from "react";
import api from "../api/api";

function ResumeUpload() {

  const [file, setFile] = useState(null);
  const [loading, setLoading] = useState(false);

  const uploadResume = async () => {

    if (!file) {
      alert("Please select a file first");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {

      setLoading(true);

      const res = await api.post(
        "/files/upload-resume",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
      );

      alert(res.data?.message || "Resume uploaded successfully");

      setFile(null);

    } catch (err) {

      console.error("Upload error:", err);

      alert(
        err.response?.data?.message || "Upload failed"
      );

    } finally {

      setLoading(false);

    }

  };

  return (

    <div className="max-w-xl mx-auto px-6 py-10">

      <div className="bg-white shadow-md rounded-xl p-8">

        <h2 className="text-2xl font-bold mb-6">
          Upload Resume
        </h2>

        <input
          type="file"
          accept=".pdf,.doc,.docx"
          onChange={(e) => setFile(e.target.files[0])}
          className="border border-gray-300 p-3 w-full rounded-lg mb-4"
        />

        {file && (
          <p className="text-sm text-gray-600 mb-4">
            Selected file: <b>{file.name}</b>
          </p>
        )}

        <button
          onClick={uploadResume}
          disabled={loading}
          className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition disabled:opacity-50"
        >
          {loading ? "Uploading..." : "Upload Resume"}
        </button>

      </div>

    </div>

  );
}

export default ResumeUpload;