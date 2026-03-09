import { useEffect, useState, useContext } from "react";
import api from "../api/api";
import { AuthContext } from "../context/AuthContext";

function NotificationBell() {

  const { userId, token } = useContext(AuthContext);

  const [notifications, setNotifications] = useState([]);

  useEffect(() => {

    if (!token || !userId) return;

    fetchNotifications();

    const interval = setInterval(fetchNotifications, 5000);

    return () => clearInterval(interval);

  }, [userId, token]);

  const fetchNotifications = async () => {

    try {

      const res = await api.get(`/notifications/user/${userId}`);

      setNotifications(res.data.data || []);

    } catch (err) {

      console.warn("Failed to load notifications");

    }

  };

  return (

    <div className="relative cursor-pointer text-xl">

      🔔

      {notifications.length > 0 && (
        <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs px-2 py-0.5 rounded-full">
          {notifications.length}
        </span>
      )}

    </div>

  );

}

export default NotificationBell;