pipeline {
    agent any

    stages {
        stage("Generate Files") {
            steps {
                sh "echo \"from http.server import HTTPServer, BaseHTTPRequestHandler\" > app.py"
                sh "echo \"class Handler(BaseHTTPRequestHandler):\" >> app.py"
                sh "echo \"    def do_GET(self):\" >> app.py"
                sh "echo \"        self.send_response(200)\" >> app.py"
                sh "echo \"        self.end_headers()\" >> app.py"
                sh "echo \"        self.wfile.write(b\\\"Jenkins Container Build Successful!\\\")\" >> app.py"
                sh "echo \"HTTPServer((\\\"0.0.0.0\\\", 8000), Handler).serve_forever()\" >> app.py"

                sh "echo \"FROM python:3.11-slim\" > Dockerfile"
                sh "echo \"WORKDIR /app\" >> Dockerfile"
                sh "echo \"COPY app.py .\" >> Dockerfile"
                sh "echo \"EXPOSE 8000\" >> Dockerfile"
                sh "echo \"CMD [\\\"python\\\", \\\"app.py\\\"]\" >> Dockerfile"
            }
        }

        stage("Build Image") {
            steps {
                sh "docker build -t my-web-app:1 ."
            }
        }

        stage("Verify Image") {
            steps {
                sh "docker images | grep my-web-app"
            }
        }
    }
}
