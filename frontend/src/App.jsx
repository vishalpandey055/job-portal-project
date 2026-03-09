import { useLocation } from "react-router-dom";
import AppRoutes from "./routes/AppRoutes";
import Navbar from "./components/Navbar";

function App() {

  const location = useLocation();

  // Hide navbar on auth pages
  const hideNavbar =
    location.pathname === "/login" || location.pathname === "/register";

  return (

    <div className="min-h-screen flex flex-col bg-gray-50">

      {/* Navbar */}
      {!hideNavbar && <Navbar />}

      {/* Main Content */}
      <main className="flex-1 w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">

        <AppRoutes />

      </main>

      {/* Footer */}
      <footer className="bg-white border-t">

        <div className="max-w-7xl mx-auto px-6 py-6 text-center text-sm text-gray-500">

          © {new Date().getFullYear()} JobPortal. All rights reserved.

        </div>

      </footer>

    </div>

  );

}

export default App;