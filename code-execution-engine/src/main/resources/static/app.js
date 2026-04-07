// ================= AUTH =================
function getAuth() {
    let user = localStorage.getItem("user") || sessionStorage.getItem("user");
    let pass = localStorage.getItem("pass") || sessionStorage.getItem("pass");

    if (!user || !pass) {
        alert("Please login first");
        window.location.href = "login.html";
        return;
    }

    return "Basic " + btoa(user + ":" + pass);
}

// ================= LOGIN =================
function login() {
    let user = document.getElementById("username").value;
    let pass = document.getElementById("password").value;
    let remember = document.getElementById("rememberMe").checked;

    if (remember) {
        localStorage.setItem("user", user);
        localStorage.setItem("pass", pass);
    } else {
        sessionStorage.setItem("user", user);
        sessionStorage.setItem("pass", pass);
    }

    window.location.href = "problems.html";
}

// ================= LOGOUT =================
function logout() {
    localStorage.clear();
    sessionStorage.clear();
    window.location.href = "login.html";
}

// ================= DEFAULT CODE =================
function getDefaultCode() {
    return `import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Write your code here
        
        
    }
}`;
}

// ================= LOAD PROBLEMS =================
function loadProblems() {
    fetch("/api/problems", {
        headers: { "Authorization": getAuth() }
    })
    .then(res => res.json())
    .then(data => {

        let html = "";

        if (!data || data.length === 0) {
            html = "<p>No problems found</p>";
        } else {
            data.forEach(p => {
                html += `
                    <div class="card">
                        <h3>${p.title}</h3>
                        <p>${p.description}</p>
                        <button onclick="openProblem(${p.id})">Solve</button>
                    </div>
                `;
            });
        }

        document.getElementById("problemList").innerHTML = html;
    })
    .catch(err => {
        console.error(err);
        document.getElementById("problemList").innerHTML = "Error loading problems";
    });
}

// ================= OPEN PROBLEM =================
function openProblem(id) {
    localStorage.setItem("problemId", id);
    window.location.href = "editor.html";
}

// ================= LOAD PROBLEM DETAILS =================
function loadProblemDetails() {

    let id = localStorage.getItem("problemId");

    if (!id) {
        alert("No problem selected");
        window.location.href = "problems.html";
        return;
    }

    fetch("/api/problems/" + id, {
        headers: { "Authorization": getAuth() }
    })
    .then(res => res.json())
    .then(data => {

        document.getElementById("problemTitle").innerText = data.title;
        document.getElementById("problemDesc").innerText = data.description;

        // ================= SAMPLE TEST CASE =================
        if (!data.testCases || data.testCases.length === 0) {
            document.getElementById("sampleInput").innerText = "No sample input";
            document.getElementById("sampleOutput").innerText = "No sample output";
        } else {

            let samples = data.testCases.filter(tc => tc.sample === true);

            if (samples.length === 0) {
                document.getElementById("sampleInput").innerText = "No sample input";
                document.getElementById("sampleOutput").innerText = "No sample output";
            } else {
                document.getElementById("sampleInput").innerText =
                    samples.map(tc => tc.input).join("\n\n");

                document.getElementById("sampleOutput").innerText =
                    samples.map(tc => tc.expectedOutput).join("\n\n");
            }
        }

        // ================= LOAD CODE =================
        if (typeof editor !== "undefined") {

            let saved = localStorage.getItem("code_" + id);

            if (saved) {
                editor.setValue(saved);   // previous code
            } else {
                editor.setValue(getDefaultCode()); // starter template
            }
        }
    })
    .catch(err => {
        console.error(err);
        alert("Error loading problem");
    });
}

// ================= AUTO SAVE CODE =================
function autoSaveCode() {
    let id = localStorage.getItem("problemId");

    if (!id || typeof editor === "undefined") return;

    localStorage.setItem("code_" + id, editor.getValue());
}

// ================= RUN CODE =================
function runCode() {

    const code = editor.getValue();
    const input = document.getElementById("input").value;

    document.getElementById("output").innerHTML = "Running...";

    fetch("/api/code/run", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": getAuth()
        },
        body: JSON.stringify({ code, input })
    })
    .then(res => res.json())
    .then(data => {

        if (data.success) {
            document.getElementById("output").innerHTML =
                "<b>Output:</b><br>" + data.output;
        } else {
            document.getElementById("output").innerHTML =
                "<b>Error:</b><br>" + data.error;
        }
    });
}

// ================= SUBMIT CODE =================
function submitCode() {

    document.getElementById("output").innerHTML = "Submitting...";

    let pid = localStorage.getItem("problemId");

    fetch("/api/code/submit-save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": getAuth()
        },
        body: JSON.stringify({
            code: editor.getValue(),
            problemId: parseInt(pid),
            username: localStorage.getItem("user")
        })
    })
    .then(res => res.json())
    .then(data => {

        document.getElementById("output").innerHTML =
            "<b>Status:</b> " + data.status +
            "<br><b>Passed:</b> " +
            data.passedTestCases + "/" + data.totalTestCases;
    });
}

// ================= LOAD SUBMISSIONS =================
function loadSubmissions() {

    fetch("/api/code/my-submissions", {
        headers: { "Authorization": getAuth() }
    })
    .then(res => res.json())
    .then(data => {

        let html = `
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Problem</th>
                <th>Status</th>
                <th>Passed</th>
            </tr>
        `;

        data.forEach(s => {

            let color = (s.status === "ACCEPTED") ? "green" : "red";

            html += `<tr onclick="openSubmission(${s.id})">
                        <td>${s.id}</td>
                        <td>${s.problemTitle}</td>
                        <td style="color:${color}; font-weight:bold;">
                            ${s.status}
                        </td>
                        <td>${s.passedTestCases}/${s.totalTestCases}</td>
                    </tr>`;
        });

        html += "</table>";

        document.getElementById("submissionList").innerHTML = html;
    });
}

// ================= VIEW SUBMISSION =================
function openSubmission(id) {

    fetch("/api/code/submission/" + id)
    .then(res => res.json())
    .then(data => {

        let testCaseHTML = "";

        if (data.testCases) {
            data.testCases.forEach(tc => {
                let color = tc.status === "PASS" ? "green" : "red";

                testCaseHTML += `
                    <div>
                        Test ${tc.index}: 
                        <span style="color:${color}">${tc.status}</span>
                    </div>`;
            });
        }

        document.getElementById("popup").style.display = "block";

        document.getElementById("popupContent").innerHTML =
            "<b>Status:</b> " + data.status + "<br><br>" +
            "<b>Passed:</b> " + data.passedTestCases + "/" + data.totalTestCases + "<br><br>" +
            "<pre>" + data.code + "</pre>" +
            testCaseHTML;
    });
}

// ================= COPY CODE =================
function copyCode() {
    let code = document.getElementById("popupContent").innerText;

    navigator.clipboard.writeText(code)
    .then(() => alert("Copied ✅"));
}

// ================= RUN AGAIN =================
function runAgain() {

    let code = document.getElementById("popupContent").innerText;
    let pid = localStorage.getItem("problemId");

    localStorage.setItem("code_" + pid, code);

    window.location.href = "editor.html";
}

// ================= CLOSE POPUP =================
function closePopup() {
    document.getElementById("popup").style.display = "none";
}

// ================= AUTO INIT =================
window.addEventListener("load", () => {

    if (document.getElementById("problemList")) {
        loadProblems();
    }

    if (document.getElementById("problemTitle")) {
        loadProblemDetails();
    }

    // auto save every 2 sec
    setInterval(autoSaveCode, 2000);
});
function loadLeaderboard() {

    let currentUser = localStorage.getItem("user");

    fetch("/api/leaderboard")
    .then(res => res.json())
    .then(data => {

        let html = `
        <table style="width:100%; border-collapse: collapse;">
            <tr style="background:#1e293b;">
                <th>Rank</th>
                <th>User</th>
                <th>Total</th>
                <th>Accepted</th>
                <th>Accuracy</th>
            </tr>
        `;

        data.forEach(u => {

            let medal = "";
            if (u.rank === 1) medal = "🥇";
            else if (u.rank === 2) medal = "🥈";
            else if (u.rank === 3) medal = "🥉";

            let highlight = (u.username === currentUser)
                ? "background:#064e3b; font-weight:bold;"
                : "";

            html += `
                <tr style="${highlight}">
                    <td>${medal} ${u.rank}</td>
                    <td>${u.username}</td>
                    <td>${u.totalSubmissions}</td>
                    <td>${u.acceptedSubmissions}</td>
                    <td>${u.accuracy.toFixed(1)}%</td>
                </tr>
            `;
        });

        html += "</table>";

        document.getElementById("leaderboardList").innerHTML = html;
    })
    .catch(err => {
        console.error(err);
        document.getElementById("leaderboardList").innerHTML = "Error loading leaderboard";
    });
}