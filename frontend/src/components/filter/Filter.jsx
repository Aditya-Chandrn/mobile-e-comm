import React from "react";
import styles from "./filter.module.css";

const companyList = ["Samsung", "Xiami", "Apple", "Motorola", "OnePlus", "Oppo", "Vivo"];

const Filter = ({
  name,
  setName,
  companies,
  setCompanies,
  lowerPrice,
  setLowerPrice,
  upperPrice,
  setUpperPrice,
  getProducts
}) => {
  const handCompanySelection = company => {
    if (companies.includes(company))
      setCompanies(companies.filter(item => item !== company));
    else setCompanies([...companies, company]);
  };

  return (
    <div className={styles["filter"]}>
      <input
        type="search"
        value={name}
        onChange={e => setName(e.target.value)}
        placeholder="Search..."
      />
      <div className={styles["filter-option"]}>
        <b>Brands</b>
        {companyList.map((company, index) =>
          <div key={index} className={styles["company"]}>
            <label>
              {company}
            </label>
            <input
              type="checkbox"
              checked={companies.includes(company.toLowerCase())}
              onChange={() => handCompanySelection(company.toLowerCase())}
            />
          </div>
        )}
      </div>
      <div className={styles["filter-option"]}>
        <b>Lower Price</b>
        <input
          type="number"
          value={lowerPrice}
          onChange={e => setLowerPrice(e.target.value)}
        />
      </div>
      <div className={styles["filter-option"]}>
        <b>Upper Price</b>
        <input
          type="number"
          value={upperPrice}
          onChange={e => setUpperPrice(e.target.value)}
        />
      </div>
      <button type="button" onClick={() => getProducts()}>Search</button>
    </div>
  );
};

export default Filter;
