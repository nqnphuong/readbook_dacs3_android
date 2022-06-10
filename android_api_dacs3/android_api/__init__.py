import cloudinary
from flask import Flask
import os
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate
import setting

db = SQLAlchemy()


def create_database(app):
    if not os.path.exists("android_api_dacs3/" + "nqnphuong.db"):
        db.create_all(app=app)


def create_app():
    app = Flask(__name__)

    ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif'}
    cloudinary.config(cloud_name="dqbktgymd", api_key="187563468568422",
                      api_secret="4kUFB1aVl8rf3Tv9kl2nFOSA3vg")

    app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True
    app.config['SQLALCHEMY_DATABASE_URI'] = f"postgresql://" \
                                            f"{setting.DB_USER}:" \
                                            f"{setting.DB_PASSWORD}@" \
                                            f"{setting.DB_HOST}:" \
                                            f"{setting.DB_PORT}/" \
                                            f"{setting.DB_NAME}"


    db = SQLAlchemy(app)
    migrate = Migrate(app, db)
    db.init_app(app)

    from .models import User, Category, Book, Chapter, ImageOfChapter, Category_Book, Library, Comment
    create_database(app)

    from android_api.views.user import user
    from android_api.views.category import category
    from android_api.views.book import book
    from android_api.views.chapter import chapter
    from android_api.views.category_book import category_book
    from android_api.views.library import library
    from android_api.views.imageOfChapter import imageOfChapter
    from android_api.views.comment import comment

    app.register_blueprint(user)
    app.register_blueprint(category)
    app.register_blueprint(book)
    app.register_blueprint(chapter)
    app.register_blueprint(category_book)
    app.register_blueprint(library)
    app.register_blueprint(imageOfChapter)
    app.register_blueprint(comment)

    return app
