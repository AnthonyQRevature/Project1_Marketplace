import { useContext, useState, useEffect } from "react";
import "./Report.css";
import { Link, useNavigate } from "react-router";
import AuthenticationContext, { type AuthenticationState } from "../authentication/AuthenticationContext";
import useLoader from "../util/AsyncLoader";
import { EncodedImage } from "../util/EncodedImage";
import LoadBrief from "../util/UserBrief";
import { useAuthGuard } from "../util/AuthGuard";
import { Role } from "../util/DataStructure";

const API_BASE = "http://localhost:8080";

interface ReportEntity {
  report_id: number;
  reporter_id: number;
  reported_id: number;
  post_id?: number;
  message_id?: number;
  reason: string;
  status: "open" | "resolved";
}

type UserProfile = {
  id : number,
  username : string,
  email : string,
  profile: 
  {
    pfp_encoded : string,
    bio : string,
    latitude : number,
    longitude : number,
    distance : number
  }
}

function Report() {
  const [auth, _] = useContext(AuthenticationContext);
//const [activeTab, setActiveTab] = useState<"create" | "view">("create");
  const [message, setMessage] = useState<{ type: "success" | "error"; text: string } | null>(null);
  const guard = useAuthGuard(Role.ADMIN);

  if (guard())
  {
    return <></>;
  }

  /*
  const fetchStatus = async () => {
    try {
      const response = await fetch(`${API_BASE}/users/ADMIN`, {
        method: "GET",
        headers: {
          Authorization: auth.encryptedToken
        }
      });

      if (response.ok) {
        setAdmin(true);
      } else {
        setAdmin(false);
      }
    } catch (e) {
      setMessage({ type: "error", text: "Server error 1" });
      console.error(e);
    }
  };
  */

  /*
  useEffect(() => {
    fetchStatus();
  }, []);
  */


  return (
    <div className="report-container">
      {message && (
        <div className={message.type === "success" ? "success-message" : "error-message"}>
          {message.text}
        </div>
      )}

      <ViewAllReports auth={auth} setMessage={setMessage} />
    </div>
  );
  /* <div className="tabs">
        <button 
          className={`tab ${activeTab === "create" ? "active" : ""}`}
          onClick={() => setActiveTab("create")}
        >
          Create Report
        </button>
        {admin && (
          <button 
            className={`tab ${activeTab === "view" ? "active" : ""}`}
            onClick={() => setActiveTab("view")}
          >
            View All Reports
          </button>
        )}
          
      {activeTab === "create" && <CreateReport auth={auth} setMessage={setMessage} />}
      </div>*/
}

/*
function CreateReport({ 
  auth, 
  setMessage 
}: { 
  auth: any; 
  setMessage: (msg: { type: "success" | "error"; text: string } | null) => void;
}) {
  const [reportedUserId, setReportedUserId] = useState("");
  const [reason, setReason] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setMessage(null);

    try {
      const body = {
        reporter_id: auth.id,
        reported_id: parseInt(reportedUserId),
        post_id: null,
        message_id: null,
        reason: reason
      };

      const response = await fetch(`${API_BASE}/users/${auth.id}/reports`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: auth.encryptedToken
        },
        body: JSON.stringify(body)
      });

      if (response.ok) {
        setMessage({ type: "success", text: "Report submitted successfully." });
        setReportedUserId("");
        setReason("");
      } else if (response.status === 400) {
        setMessage({ type: "error", text: "Error: 400 Please check your input, you are likely reporting yourself." });
      } else if (response.status === 401) {
        setMessage({ type: "error", text: "Error: 401 You are likely not logged in, somehow." });
      } else if (response.status === 403) {
        setMessage({ type: "error", text: "Error: 403 You are likely not logged in, somehow." });
      } else if (response.status === 500) {
        setMessage({ type: "error", text: "Error: 500 Please check your input, you are likely reporting a nonvalid id." });
      } else {
        setMessage({ type: "error", text: `Error: ${response.status}` });
      }
    } catch (e) {
      setMessage({ type: "error", text: "Server error, please try again later." });
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  if (auth.id === -1) {
    return <p>You must be logged in to create a report.</p>;
  }

  return (
    <form className="report-form" onSubmit={handleSubmit}>
      <div>
        <label htmlFor="reported_user_id">User ID to Report:</label>
        <input 
          type="number" 
          id="reported_user_id"
          value={reportedUserId}
          onChange={(e) => setReportedUserId(e.target.value)}
          required 
          min="0"
        />
      </div>
      <div>
        <label htmlFor="reason">Reason:</label>
        <textarea 
          id="reason"
          value={reason}
          onChange={(e) => setReason(e.target.value)}
          required
          placeholder="Describe why you are reporting this user..."
        />
      </div>
      <div>
        <input type="submit" value={loading ? "Submitting..." : "Submit Report"} disabled={loading} />
      </div>
    </form>
  );
}
*/

function ViewAllReports({ 
  auth, 
  setMessage 
}: { 
  auth: any; 
  setMessage: (msg: { type: "success" | "error"; text: string } | null) => void;
}) {
  const [reports, setReports] = useState<ReportEntity[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchReports = async () => {
    setLoading(true);
    try {
      const response = await fetch(`${API_BASE}/reports`, {
        method: "GET",
        headers: {
          Authorization: auth.encryptedToken
        }
      });

      if (response.ok) {
        const data = await response.json();
        setReports(data);
      } else {
        setMessage({ type: "error", text: "Failed to fetch reports" });
      }
    } catch (e) {
      setMessage({ type: "error", text: "Server error 2" });
      console.error(e);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReports();
  }, []);

  const changeStatus = async (report_id: number, newStatusNumber: number) => {
    try {
      const body = {
        id: report_id,
        status: newStatusNumber
      };

      console.log("Sending status change:", body);

      const response = await fetch(`${API_BASE}/reports`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: auth.encryptedToken
        },
        body: JSON.stringify(body)
      });

      console.log("Response status:", response.status);

      if (response.ok) {
        setMessage({ type: "success", text: "Report status updated!" });
        fetchReports();
      } else {
        setMessage({ type: "error", text: `Failed to update status: ${response.status}` });
      }
    } catch (e) {
      setMessage({ type: "error", text: "Server error 3" });
      console.error(e);
    }
  };

  const deleteReport = async (report_id: number) => {
    if (!window.confirm("Are you sure you want to delete this report, and all associated content?")) return;

    try {
      const response = await fetch(`${API_BASE}/reports/${report_id}`, {
        method: "DELETE",
        headers: {
          Authorization: auth.encryptedToken
        }
      });

      if (response.ok) {
        setMessage({ type: "success", text: "Report and content deleted!" });
        fetchReports();
      } else {
        const errorText = await response.text();
        console.error("Error response:", errorText);
        setMessage({ type: "error", text: `Failed to delete report: ${response.status}` });
      }
    } catch (e) {
      setMessage({ type: "error", text: "Server error" });
      console.error(e);
    }
  };

  const purgeReport = async (report_id: number) => {
    if (!window.confirm("Are you sure you want to purge this report? It will delete any associated message or post.")) return;

    try {
      const response = await fetch(`${API_BASE}/reports/${report_id}/purge`, {
        method: "DELETE",
        headers: {
          Authorization: auth.encryptedToken
        }
      });

      if (response.ok) {
        setMessage({ type: "success", text: "Content purged!" });
        fetchReports();
      } else {
        const errorText = await response.text();
        console.error("Error response:", errorText);
        setMessage({ type: "error", text: `Failed to delete content: ${response.status}` });
      }
    } catch (e) {
      setMessage({ type: "error", text: "Server error" });
      console.error(e);
    }
  }

  if (loading && reports.length === 0) {
    return <p>Loading reports...</p>;
  }

  if (reports.length === 0) {
    return <p>No reports found.</p>;
  }

  return (
    <div className="reports-list">
      <h2>All Reports ({reports.length})</h2>
      {reports.map((report) => (
        <div key={report.report_id} className={`report-item ${report.status.toLowerCase()}`}>
          <div className="report-header">
            <div>
              <strong>Report #{report.report_id}</strong>
              <LoadBrief lhs="Reporter:" user_id={report.reporter_id}/>
              <LoadBrief lhs="Reported:" user_id={report.reported_id}/>
            </div>
            <span className={`report-status ${report.status.toLowerCase()}`}>
              {report.status}
            </span>
          </div>
          <p><strong>Reason:</strong> {report.reason}</p>
          
          <div className="report-actions">
            {report.status !== "resolved" && (
              <button 
                className="btn-resolve" 
                onClick={() => changeStatus(report.report_id, 1)}
              >
                Resolve
              </button>
            )}
            {report.status !== "open" && (
              <button 
                className="btn-dismiss" 
                onClick={() => changeStatus(report.report_id, 0)}
              >
                Reopen
              </button>
            )}
            <button 
              className="btn-delete" 
              onClick={() => deleteReport(report.report_id)}
            >
              Delete
            </button>
            {((report.message_id != null || report.post_id != null) && report.status !== "resolved") && (
              <button
                className="btn-delete"
                onClick={() => purgeReport(report.report_id)}
                >
                  Purge
                </button>
            )}
          </div>
        </div>
      ))}
    </div>
  );
}

export default Report;