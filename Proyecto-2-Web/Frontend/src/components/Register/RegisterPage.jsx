import React from "react";
import { RegisterForm } from "./RegisterForm";
import "./RegisterPage.css";
import Navigation from "../Navigation/Navigation";

export function RegisterPage() {
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
          <section className="rightColumn">
            <RegisterForm />
          </section>
        </div>
      </main>
    </>
  );
}
