from android_api import create_app
from flask import render_template

app = create_app()


@app.route("/")
@app.route("/api")
def home_page():
    return render_template('home.html')



if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
