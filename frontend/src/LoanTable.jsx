import { money } from "./api";

const FILTERS = [["", "All"], ["PENDING", "Pending"], ["APPROVED", "Approved"], ["REJECTED", "Rejected"]];

export default function LoanTable({ loans, filter, setFilter, onEdit, onStatus, onDelete }) {
  return (
    <section className="panel">
      <div className="table-head">
        <h2>Applications</h2>
        <div className="filters" role="tablist">
          {FILTERS.map(([v, l]) => (
            <button key={v} role="tab" aria-selected={filter === v}
              className={filter === v ? "chip active" : "chip"} onClick={() => setFilter(v)}>{l}</button>
          ))}
        </div>
      </div>

      {loans.length === 0 ? (
        <p className="empty">No applications here yet. Submit one using the form.</p>
      ) : (
        <div className="scroll">
          <table>
            <thead>
              <tr>
                <th>Student</th><th>Course</th><th>Amount</th><th>EMI</th><th>Status</th><th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {loans.map((l) => (
                <tr key={l.id}>
                  <td>
                    <strong>{l.studentName}</strong>
                    <div className="sub">{l.email}</div>
                  </td>
                  <td>
                    {l.course}
                    <div className="sub">{l.university}</div>
                  </td>
                  <td>
                    {money(l.loanAmount)}
                    <div className="sub">{l.tenureMonths} months</div>
                  </td>
                  <td>{money(l.emi)}</td>
                  <td><span className={`badge ${l.status.toLowerCase()}`}>{l.status.toLowerCase()}</span></td>
                  <td className="row-actions">
                    {l.status === "PENDING" && (
                      <>
                        <button className="ok" onClick={() => onStatus(l, "APPROVED")}>Approve</button>
                        <button className="no" onClick={() => onStatus(l, "REJECTED")}>Reject</button>
                        <button className="ghost" onClick={() => onEdit(l)}>Edit</button>
                      </>
                    )}
                    <button className="ghost danger" onClick={() => onDelete(l)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  );
}
