import React, { useEffect, useState } from 'react';
import styles from "./orders.module.css";
import { getUser } from 'helperFunctions';

const Orders = () => {
  const user = getUser();
  const [order, setOrders] = useState([]);

  const getOrders = () => {

  }

  useEffect(() => {

  })

  return (
    <div className={styles["order"]}>
      
    </div>
  )
}

export default Orders;