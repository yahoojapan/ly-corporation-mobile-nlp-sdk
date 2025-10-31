import Foundation

let args = CommandLine.arguments
let inputPath = args[1]
let outputPath = args[2]

guard let content = try? String(contentsOfFile: inputPath, encoding: .utf8) else {
    fputs("Error: could not read \(inputPath)\n", stderr)
    exit(1)
}

func dumpEnv(_ envs: [String: String]) -> String {
    envs.map { key, value in
        "static let \(key) = \"\(value)\""
    }.joined(separator: "\n    ")
}

let envs: [String: String] = content
    .split(whereSeparator: \.isNewline)
    .map { $0.trimmingCharacters(in: .whitespaces) }
    .map { line -> (String, String) in
        let parts = line.split(separator: "=", maxSplits: 1)
        guard parts.count == 2 else {
            fputs("Error: failed to parse env", stderr)
            exit(1)
        }
        return (String(parts[0]), String(parts[1]))
    }.reduce(into: [String: String]()) { acc, pair in
        acc[pair.0.uppercased()] = pair.1
    }

let src = """
import Foundation

enum AppSecrets {
    \(dumpEnv(envs))
}
"""

guard let content = try? src.write(toFile: outputPath, atomically: true, encoding: .utf8) else {
    fputs("Error: failed to write app secrets", stderr)
    exit(1)
}

print("generated AppSecrets at: \(outputPath)")
