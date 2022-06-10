from flask import request, jsonify
from android_api_dacs3.android_api.controllers.imgaeOfChapter import create_imageOfChapter, show_by_id_imageOfChapter,delete_imageOfChapter,update_imageOfChapter,show_all_imageOfChapter
from flask_cors import cross_origin
from flask import Blueprint

imageOfChapter = Blueprint("imageOfChapter", __name__)


@imageOfChapter.route("/api/imageOfChapter/create", methods=["GET", "POST"])
@cross_origin()
def imageOfChapter_create():
    res = request.json
    for key, value in res.items():
        if value == None:
            return jsonify({"msg": "add imageOfChapter fail", "error": "bad request"}, 401)
        add_status = create_imageOfChapter(
            id_chapter=res.get("id_chapter"),
            image=res.get("image")
        )
        if add_status:
            return jsonify({"msg": "add imageOfChapter success", "data": res}, 200)
        else:
            return jsonify({"msg": "no create imageOfChapter", "data": None}, 401)


@imageOfChapter.route("/api/imageOfChapter/read_all", methods=["GET"])
@cross_origin()
def imageOfChapter_readAll():
    imageOfChapter = show_all_imageOfChapter()
    if imageOfChapter == None:
        return jsonify({"msg": "no imageOfChapter", "data": None}, 500)
    else:
        return jsonify(imageOfChapter), 200


@imageOfChapter.route("/api/imageOfChapter/read_by_id/<int:id_imageOfChapter>", methods=["GET"])
@cross_origin()
def imageOfChapter_readByID(id_imageOfChapter):
    imageOfChapter = show_by_id_imageOfChapter(id_imageOfChapter)
    if imageOfChapter == None:
        return jsonify({"msg": "no imageOfChapter", "data": None}, 500)
    else:
        return jsonify(imageOfChapter), 200


@imageOfChapter.route("/api/imageOfChapter/update/<int:id_imageOfChapter>", methods=["PUT"])
@cross_origin()
def user_update(id_imageOfChapter):
    res = request.json
    for key, value in res.items():
        if value == None:
            return jsonify({"msg": "update imageOfChapter fail", "error": "bad request"}, 401)
        update_status = update_imageOfChapter(
            id_imageOfChapter,
            id_chapter=res.get("id_chapter"),
            image=res.get("image"))
    if update_status:
        return jsonify({"msg": "update imageOfChapter success", "data": res}, 200)
    else:
        return jsonify({"msg": "no update", "data": None}, 401)


@imageOfChapter.route("/api/imageOfChapter/delete/<int:id_imageOfChapter>", methods=["DELETE"])
@cross_origin()
def user_delete(id_user):
    imageOfChapter = delete_imageOfChapter(id_user)
    if imageOfChapter == None:
        return jsonify({"msg": "no delete", "data": None}, 500)
    else:
        return jsonify({"msg": "delete success", "data": imageOfChapter}, 200)
