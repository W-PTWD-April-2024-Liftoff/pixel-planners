import React from 'react';
import { Link } from 'react-router-dom';
import Header from '../welcome/Header';
import styles from '../../styles/welcome/Welcome.module.css'; // Import the CSS file

const Welcome = () => {
  return (
    <div className={styles['welcome-container']}>
    <Header />
    <div className={styles['welcome-content']}>
      <h1>WELCOME</h1>


      <p>The portal will allow you to create and manage events</p>

      </div>

    </div>
  );
};

export default Welcome;