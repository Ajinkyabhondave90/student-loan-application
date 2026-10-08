import { useCallback, useEffect, useState } from "react";
import { api } from "./api";
import LoanForm from "./LoanForm";
import LoanTable from "./LoanTable";
import Stats from "./Stats";

export default function App() {
  const [loans, setLoans] = useState([]);
  const [stats, setStats] = useState({});
  const [filter, setFilter] = useState("");
  const [editing, setEditing] = useState(null);
  const [toast, setToast] = useState(null);
  const [loadError, setLoadError] = useState("");

  const notify = (message, isError = false) => {
    setToast({ message, isError });
    setTimeout(() => setToast(null), 3000);
  };

  const load = useCallback(async () => {
    try {
      const [l, s] = await Promise.all([api.list(filter), api.stats()]);
      setLoans(l);
      setStats(s);
      setLoadError("");
    } catch {
      setLoadError("Cannot reach the server. Check that the Spring Boot backend is running on port 8080.");
    }
  }, [filter]);

  useEffect(() => { load(); }, [load]);

  async function changeStatus(loan, status) {
    const remarks = window.prompt(`Add a note for this ${status.toLowerCase()} decision (optional)`) ?? "";
    try {
      await api.setStatus(loan.id, status, remarks);
      notify(status === "APPROVED" ? "Application approved" : "Application rejected");
      load();
    } catch (e) { notify(e.message, true); }
  }

  async function remove(loan) {
    if (!window.confirm(`Delete ${loan.studentName}'s application?`)) return;
    try {
      await api.remove(loan.id);
      notify("Application deleted");
      if (editing?.id === loan.id) setEditing(null);
      load();
    } catch (e) { notify(e.message, true); }
  }

  return (
    <div className="app">
      <header>
        <h1>Student Loan Desk</h1>
        <p>Apply, review and track education loan applications.</p>
      </header>

      {loadError && <div className="banner">{loadError}</div>}
      <Stats stats={stats} />

      <main>
        <LoanForm
          editing={editing}
          notify={notify}
          onCancel={() => setEditing(null)}
          onSaved={() => { setEditing(null); load(); }}
        />
        <LoanTable
          loans={loans}
          filter={filter}
          setFilter={setFilter}
          onEdit={(l) => { setEditing(l); window.scrollTo({ top: 0, behavior: "smooth" }); }}
          onStatus={changeStatus}
          onDelete={remove}
        />
      </main>

      {toast && <div className={toast.isError ? "toast error-toast" : "toast"} role="status">{toast.message}</div>}
    </div>
  );
}
