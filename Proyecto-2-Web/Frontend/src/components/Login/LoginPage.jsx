import React from "react";
import { LoginForm } from "./LoginForm";
import "./LoginPage.css";
import Navigation from "../Navigation/Navigation";

export function LoginPage() {
  return (
    <>
      <Navigation/>
      <main className="authContainer">
        <div className="contentWrapper">
          <aside className="leftColumn">
            <div
              className="bannerImage"
              role="img"
              aria-label="Authentication banner"
            ></div>
          </aside>
          <aside className="rightColumn">
            <LoginForm />
          </aside>
        </div>
      </main>
    </>
  );
}