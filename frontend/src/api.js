const BASE = "/api/loans";

async function request(url, options = {}) {
  const res = await fetch(url, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });
  if (res.status === 204) return null;
  const data = await res.json().catch(() => ({}));
  if (!res.ok) {
    const err = new Error(data.message || "Something went wrong");
    err.fieldErrors = data.errors || {};
    throw err;
  }
  return data;
}

export const api = {
  list: (status) => request(status ? `${BASE}?status=${status}` : BASE),
  stats: () => request(`${BASE}/stats`),
  create: (body) => request(BASE, { method: "POST", body: JSON.stringify(body) }),
  update: (id, body) => request(`${BASE}/${id}`, { method: "PUT", body: JSON.stringify(body) }),
  setStatus: (id, status, remarks) =>
    request(`${BASE}/${id}/status`, { method: "PATCH", body: JSON.stringify({ status, remarks }) }),
  remove: (id) => request(`${BASE}/${id}`, { method: "DELETE" }),
};

export const money = (n) =>
  new Intl.NumberFormat("en-IN", { style: "currency", currency: "INR", maximumFractionDigits: 0 }).format(n ?? 0);
