import React from "react";
import styles from "./navbar.module.css";
import { Link } from "react-router-dom";
import { getUser } from "helperFunctions";

const Navbar = ({ children }) => {
  const user = getUser();

  return (
    <div className={styles["navbar"]}>
      <div className={styles["nav-options"]}>
        <Link to="/">Home</Link>
        <Link to="/sell">Sell</Link>
        <Link to="/orders">Orders</Link>
        <Link to="/cart">Cart</Link>
        <button type="button">
          {user ? user.name : ""}
        </button>
      </div>
      {children}
    </div>
  );
};

export default Navbar;
