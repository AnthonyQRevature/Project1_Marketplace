import { Link, useParams } from "react-router";
import type { Endpoint } from "../util/Endpoint";
import { useContext, useEffect, useState } from "react";
import AuthenticationContext from "../authentication/AuthenticationContext";
import useLoader from "../util/AsyncLoader";
import "./Report.css";

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

function UsersReport() {
  const {user_id} = useParams();
  const [auth, _] = useContext(AuthenticationContext);
  const [] = useLoader<UserProfile>(makeEndpointSecond(Number(user_id)));
  const [activeTab, setActiveTab] = useState<"view">("view");
  const [message, setMessage] = useState<{ type: "success" | "error"; text: string } | null>(null);
  const [admin, setAdmin] = useState(false);
  
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
  
    useEffect(() => {
      fetchStatus();
    }, []);

  return (
    <div className="report-container">
      {message && (
        <div className={message.type === "success" ? "success-message" : "error-message"}>
          {message.text}
        </div>
      )}

      <div className="tabs">
        <button 
          className={`tab ${activeTab === "view" ? "active" : ""}`}
          onClick={() => setActiveTab("view")}
        >
          View All Reports
        </button>
      </div>

      {activeTab === "view" && <ViewUsersReport user_id={user_id} admin={admin} auth={auth} setMessage={setMessage} />}
    </div>
  );
}

function makeEndpointSecond(id: number) : Endpoint
{
  return {endpoint: `/users/${id}/reports/from`, method: "GET"};
}

function ViewUsersReport({ 
  user_id,
  admin,
  auth, 
  setMessage 
}: { 
  user_id: any;
  admin: any;
  auth: any; 
  setMessage: (msg: { type: "success" | "error"; text: string } | null) => void;
}) {
  const [reports, setReports] = useState<ReportEntity[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchReports = async () => {
    setLoading(true);
    try {
      const response = await fetch(`${API_BASE}/users/${user_id}/reports/from`, {
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
      setMessage({ type: "error", text: "Server error" });
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
        const errorText = await response.text();
        console.error("Error response:", errorText);
        setMessage({ type: "error", text: `Failed to update status: ${response.status}` });
      }
    } catch (e) {
      setMessage({ type: "error", text: "Server error" });
      console.error(e);
    }
  };

  const deleteReport = async (report_id: number) => {
    if (!window.confirm("Are you sure you want to delete this report?")) return;

    try {
      console.log("Deleting report:", report_id);

      const response = await fetch(`${API_BASE}/reports/${report_id}`, {
        method: "DELETE",
        headers: {
          Authorization: auth.encryptedToken
        }
      });

      console.log("Delete response status:", response.status);

      if (response.ok) {
        setMessage({ type: "success", text: "Report deleted!" });
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
              <Link to={`/users/${report.reporter_id}`}><p>Reporter: User {report.reporter_id}</p></Link>
              <Link to={`/users/${report.reported_id}`}><p>Reported: User {report.reported_id}</p></Link>
            </div>
            <span className={`report-status ${report.status.toLowerCase()}`}>
              {report.status}
            </span>
          </div>
          <p><strong>Reason:</strong> {report.reason}</p>
          
          {admin && (<div className="report-actions">
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
          </div>)}
        </div>
      ))}
    </div>
  );
}

export default UsersReport;