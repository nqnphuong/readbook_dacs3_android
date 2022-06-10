
import cloudinary.uploader
from flask import request, jsonify
from android_api_dacs3.android_api.controllers.book import show_by_id_book, show_all_book, create_book, delete_book, \
    update_book, category_book_show, read_your_library
from flask_cors import cross_origin
from flask import Blueprint
from android_api_dacs3.android_api import create_app

book = Blueprint("book", __name__)
app = create_app()


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in app.ALLOWED_EXTENSIONS


@book.route("/api/book/create", methods=["POST"])
@cross_origin()
def book_create():
    if request.method != "POST":
        return jsonify({"msg": "add user fail", "error": "bad request"}, 401)
    else:
        cloudinary.config(cloud_name="dqbktgymd", api_key="187563468568422",
                          api_secret="4kUFB1aVl8rf3Tv9kl2nFOSA3vg")
        pic = request.files['bookImage']
        pic_path = None
        if not pic:
            return "No pic upload", 400
        upload_result = cloudinary.uploader.upload(pic)
        pic_path = upload_result["secure_url"]
        add_status = create_book(
            bookName=request.form.get("bookName"),
            bookImage=pic_path,
            bookAuthor=request.form.get("bookAuthor"),
            bookDescription=request.form.get("bookDescription"),
            )
        if add_status:
            return jsonify({"msg": "ok"}), 200
        else:
            return jsonify({"msg": "no create user", "data": None}, 401)


@book.route("/api/book/read_all", methods=["GET"])
@cross_origin()
def book_readAll():
    book = show_all_book()
    if book == None:
        return jsonify({"msg": "no book", "data": None}, 500)
    else:
        return jsonify(book), 200


@book.route("/api/book/read_by_id/<int:id_book>", methods=["GET"])
@cross_origin()
def book_readByID(id_book):
    book = show_by_id_book(id_book)
    if book == None:
        return jsonify({"msg": "no book", "data": None}, 500)
    else:
        return jsonify(book), 200



@book.route("/api/book/update/<int:id_book>", methods=["PUT"])
@cross_origin()
def book_update(id_book):
    if request.method != "PUT":
        return jsonify({"msg": "update book fail", "error": "bad request"}, 401)
    else:
        cloudinary.config(cloud_name="dqbktgymd", api_key="187563468568422",
                          api_secret="4kUFB1aVl8rf3Tv9kl2nFOSA3vg")
        pic = request.files['bookImage']
        pic_path = None
        if not pic:
            return "No pic upload", 400
        upload_result = cloudinary.uploader.upload(pic)
        pic_path = upload_result["secure_url"]
        add_status = update_book(
            id_book,
            bookName=request.form.get("bookName"),
            bookImage=pic_path,
            bookDescription=request.form.get("bookDescription")
        )

    if add_status:
        print(id_book)
        book = show_by_id_book(id_book)
        print(book)
        return jsonify(book), 200
    else:
        return jsonify({"msg": "no update book", "data": None}, 401)



@book.route("/api/book/delete/<int:id_book>", methods=["DELETE"])
@cross_origin()
def book_delete(id_book):
    book = delete_book(id_book)
    if book == None:
        return jsonify({"msg": "no delete", "data": None}, 500)
    else:
        return jsonify({"msg": "delete success", "data": book}, 200)


@book.route("/api/book/category/read_all", methods=["GET"])
@cross_origin()
def book_category_readAll():
    book = category_book_show()
    if book == None:
        return jsonify({"msg": "no data", "data": None}, 500)
    else:
        return jsonify(book), 200


@book.route("/api/book/read_your_library/<int:id_user>", methods=["GET"])
@cross_origin()
def book_read_your_library(id_user):
    book = read_your_library(id_user)
    if book == None:
        return jsonify({"msg": "no book", "data": None}, 500)
    else:
        return jsonify(book), 200
