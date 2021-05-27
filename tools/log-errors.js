const fs = require("fs");

const logPath = "../run/logs/debug.log";

const log = fs.readFileSync(logPath, "utf-8");

const errors = [];
const warnings = [];

log.replace(/\r\n/g, '\n').split('\n').forEach((line) => 
{
	if (line.includes("ERROR"))
	{
		errors.push(line.substr(line.indexOf("ERROR") + 7, line.length));
	}
	else if (line.includes("WARN"))
		warnings.push(line.substr(line.indexOf("WARN") + 6, line.length));
});

console.log("WARNINGS:");
warnings.forEach(l => console.log(l));
console.log("");
console.log("ERRORS:");
errors.forEach(l => console.log(l));
