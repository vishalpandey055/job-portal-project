import { useEffect, useState } from "react";
import api from "../api/api";

function AdminDashboard(){

  const [analytics,setAnalytics] = useState(null);
  const [loading,setLoading] = useState(true);

  useEffect(()=>{
    fetchAnalytics();
  },[])

  const fetchAnalytics = async () => {

    try{

      const res = await api.get("/admin/analytics");

      setAnalytics(res.data.data);

    }catch(err){

      console.error(err);

    }finally{

      setLoading(false);

    }

  }

  if(loading){

    return(

      <div className="flex justify-center mt-20">
        <div className="animate-spin h-10 w-10 border-4 border-blue-600 border-t-transparent rounded-full"></div>
      </div>

    )

  }

  return(

    <div className="max-w-6xl mx-auto px-6 py-10">

      <h1 className="text-3xl font-bold mb-8">
        Admin Dashboard
      </h1>

      <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">

        {/* Users */}
        <div className="bg-white shadow-md rounded-xl p-6 hover:shadow-xl transition">

          <p className="text-gray-500 text-sm">
            Total Users
          </p>

          <h2 className="text-3xl font-bold mt-2 text-blue-600">
            {analytics.totalUsers}
          </h2>

        </div>

        {/* Jobs */}
        <div className="bg-white shadow-md rounded-xl p-6 hover:shadow-xl transition">

          <p className="text-gray-500 text-sm">
            Total Jobs
          </p>

          <h2 className="text-3xl font-bold mt-2 text-green-600">
            {analytics.totalJobs}
          </h2>

        </div>

        {/* Applications */}
        <div className="bg-white shadow-md rounded-xl p-6 hover:shadow-xl transition">

          <p className="text-gray-500 text-sm">
            Total Applications
          </p>

          <h2 className="text-3xl font-bold mt-2 text-purple-600">
            {analytics.totalApplications}
          </h2>

        </div>

      </div>

    </div>

  )

}

export default AdminDashboard;