import { money } from "./api";

export default function Stats({ stats }) {
  const items = [
    { label: "Applications", value: stats.total },
    { label: "Pending review", value: stats.pending, tone: "pending" },
    { label: "Approved", value: stats.approved, tone: "approved" },
    { label: "Rejected", value: stats.rejected, tone: "rejected" },
    { label: "Approved amount", value: money(stats.approvedAmount) },
  ];
  return (
    <section className="stats" aria-label="Summary">
      {items.map((i) => (
        <div key={i.label} className={`stat ${i.tone || ""}`}>
          <span className="stat-value">{i.value ?? 0}</span>
          <span className="stat-label">{i.label}</span>
        </div>
      ))}
    </section>
  );
}
