import React from "react";
import { createRoot } from "react-dom/client";
import App from "./components/App/App";
import "./Styles/index.css";
import { LoginProvider } from "./Context/LoginContext";
import { FetchProvider } from "./Context/FetchContext";

createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <LoginProvider>
      <FetchProvider>
        <App />
      </FetchProvider>
    </LoginProvider>
  </React.StrictMode>
);
