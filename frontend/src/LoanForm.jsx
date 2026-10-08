import { useEffect, useState } from "react";
import { api, money } from "./api";

const EMPTY = {
  studentName: "", email: "", phone: "", university: "", course: "",
  loanAmount: "", tenureMonths: "", annualIncome: "", purpose: "",
};

const FIELDS = [
  ["studentName", "Student name", "text"],
  ["email", "Email", "email"],
  ["phone", "Phone (10 digits)", "tel"],
  ["university", "University", "text"],
  ["course", "Course", "text"],
  ["loanAmount", "Loan amount (₹)", "number"],
  ["tenureMonths", "Repayment period (months)", "number"],
  ["annualIncome", "Family annual income (₹)", "number"],
];

export default function LoanForm({ editing, onSaved, onCancel, notify }) {
  const [form, setForm] = useState(EMPTY);
  const [errors, setErrors] = useState({});
  const [busy, setBusy] = useState(false);

  useEffect(() => {
    setErrors({});
    setForm(editing ? Object.fromEntries(Object.keys(EMPTY).map((k) => [k, editing[k] ?? ""])) : EMPTY);
  }, [editing]);

  const change = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  async function submit(e) {
    e.preventDefault();
    setBusy(true);
    setErrors({});
    const body = {
      ...form,
      loanAmount: Number(form.loanAmount),
      tenureMonths: Number(form.tenureMonths),
      annualIncome: Number(form.annualIncome),
    };
    try {
      editing ? await api.update(editing.id, body) : await api.create(body);
      notify(editing ? "Application updated" : "Application submitted");
      setForm(EMPTY);
      onSaved();
    } catch (err) {
      setErrors(err.fieldErrors || {});
      notify(err.message, true);
    } finally {
      setBusy(false);
    }
  }

  // Live EMI preview (9.5% fixed, matches backend default)
  const P = Number(form.loanAmount), n = Number(form.tenureMonths), r = 9.5 / 1200;
  const preview = P >= 10000 && n >= 6 ? (P * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1) : null;

  return (
    <form className="panel form" onSubmit={submit} noValidate>
      <h2>{editing ? `Edit application #${editing.id}` : "New application"}</h2>
      {FIELDS.map(([name, label, type]) => (
        <label key={name}>
          <span>{label}</span>
          <input name={name} type={type} value={form[name]} onChange={change} aria-invalid={!!errors[name]} />
          {errors[name] && <small className="error">{errors[name]}</small>}
        </label>
      ))}
      <label>
        <span>Purpose (optional)</span>
        <textarea name="purpose" rows="2" value={form.purpose} onChange={change} />
      </label>
      {preview && (
        <p className="preview">
          Estimated EMI <strong>{money(preview)}</strong> per month at 9.5% a year
        </p>
      )}
      <div className="actions">
        <button className="primary" disabled={busy}>
          {busy ? "Saving…" : editing ? "Save changes" : "Submit application"}
        </button>
        {editing && (
          <button type="button" className="ghost" onClick={onCancel}>Cancel edit</button>
        )}
      </div>
    </form>
  );
}
