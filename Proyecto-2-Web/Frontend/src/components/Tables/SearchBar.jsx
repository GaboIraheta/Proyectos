import React, { useState } from "react";
import "./SearchBar.css";

const SearchBar = ({ onSearch }) => {
  const [searchEmail, setSearchEmail] = useState("");

  const handleSearchChange = (e) => {
    setSearchEmail(e.target.value);
    onSearch(e.target.value); 
  };

  return (
    <div className="search-bar">
      <input
        type="email"
        placeholder="Buscar por email..."
        value={searchEmail}
        onChange={handleSearchChange}
      />
    </div>
  );
};

export default SearchBar;
