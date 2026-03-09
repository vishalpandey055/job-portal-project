export const formatSalary = (salary) => {

  if (!salary) return "₹0";

  return `₹${Number(salary).toLocaleString("en-IN")}`;

};