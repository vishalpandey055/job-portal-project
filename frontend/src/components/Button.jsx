function Button({ children, onClick, type = "button", className = "", disabled = false, ...props }) {
  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled}
      className={`bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md transition font-medium disabled:opacity-50 ${className}`}
      {...props}
    >
      {children}
    </button>
  );
}
export default Button;