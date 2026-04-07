// 🔐 AUTH
function getAuth() {
    const user = localStorage.getItem("user");
    const pass = localStorage.getItem("pass");
    return "Basic " + btoa(user + ":" + pass);
}

// 📋 LOAD PROBLEMS
function loadProblems() {

    fetch("/api/problems", {
        headers: { "Authorization": getAuth() }
    })
    .then(res => res.json())
    .then(data => {

        let html = "";

        data.forEach(p => {
            html += `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.title}</td>
                    <td class="actions">
                        <button onclick="editProblem(${p.id}, '${p.title}', \`${p.description}\`)">Edit</button>
                        <button class="delete-btn" onclick="deleteProblem(${p.id})">Delete</button>
                    </td>
                </tr>
            `;
        });

        document.getElementById("problemTable").innerHTML = html;
    });
}

// ➕ ADD / UPDATE
function addProblem() {

    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;

    if (!title || !description) {
        showMsg("problemMsg", "Fill all fields ❌", "red");
        return;
    }

    let method = "POST";
    let url = "/api/problems";

    if (window.editId) {
        method = "PUT";
        url = "/api/problems/" + window.editId;
    }

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": getAuth()
        },
        body: JSON.stringify({
            title: title,
            description: description,
            difficulty: "EASY"
        })
    })
    .then(res => res.json())
    .then(() => {

        showMsg("problemMsg", window.editId ? "Updated ✅" : "Added ✅", "#22c55e");

        document.getElementById("title").value = "";
        document.getElementById("description").value = "";

        window.editId = null;

        loadProblems();
    });
}

// ✏️ EDIT
function editProblem(id, title, description) {

    document.getElementById("title").value = title;
    document.getElementById("description").value = description;

    window.editId = id;

    showMsg("problemMsg", "Editing ID: " + id, "yellow");
}

// 🗑 DELETE
function deleteProblem(id) {

    if (!confirm("Delete this problem?")) return;

    fetch("/api/problems/" + id, {
        method: "DELETE",
        headers: { "Authorization": getAuth() }
    })
    .then(() => {
        showMsg("problemMsg", "Deleted ✅", "#22c55e");
        loadProblems();
    });
}

// ➕ ADD TEST CASE
function addTestCase() {

    const problemId = document.getElementById("problemId").value;
    const input = document.getElementById("input").value;
    const output = document.getElementById("output").value;
    const isSample = document.getElementById("isSample").checked;

    if (!problemId || !input || !output) {
        showMsg("testMsg", "Fill all fields ❌", "red");
        return;
    }

    fetch("/api/testcases", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": getAuth()
        },
        body: JSON.stringify({
            input: input,
            expectedOutput: output,
            sample: isSample,
            problem: { id: parseInt(problemId) }
        })
    })
    .then(() => {
        showMsg("testMsg", "Test case added ✅", "#22c55e");

        document.getElementById("problemId").value = "";
        document.getElementById("input").value = "";
        document.getElementById("output").value = "";
        document.getElementById("isSample").checked = false;
    });
}

// 🔥 MESSAGE
function showMsg(id, msg, color) {
    let el = document.getElementById(id);
    el.innerText = msg;
    el.style.color = color;
}

// 🚀 LOAD
window.onload = loadProblems;