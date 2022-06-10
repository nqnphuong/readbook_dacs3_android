from flask import request, jsonify
from android_api.controllers.comment import create_comment,show_by_id_comment,show_all_comment,delete_comment,update_comment
from flask_cors import cross_origin
from flask import Blueprint

comment = Blueprint("comment", __name__)


@comment.route("/api/comment/create", methods=["GET", "POST"])
@cross_origin()
def comment_create():
    res = request.json
    for key, value in res.items():
        if value == None:
            return jsonify({"msg": "add comment fail", "error": "bad request"}, 401)
        add_status = create_comment(
            id_user=res.get("id_user"),
            id_book=res.get("id_book"),
            comment=res.get("comment")
        )
        if add_status:
            return jsonify({"msg": "add comment success", "data": res}, 200)
        else:
            return jsonify({"msg": "no create comment", "data": None}, 401)


@comment.route("/api/comment/read_all", methods=["GET"])
@cross_origin()
def comment_readAll():
    comment = show_all_comment()
    if comment == None:
        return jsonify({"msg": "no comment", "data": None}, 500)
    else:
        return jsonify(comment), 200


@comment.route("/api/comment/read_by_id/<int:id_comment>", methods=["GET"])
@cross_origin()
def comment_readByID(id_comment):
    comment = show_by_id_comment(id_comment)
    if comment == None:
        return jsonify({"msg": "no comment", "data": None}, 500)
    else:
        return jsonify(comment), 200


@comment.route("/api/comment/update/<int:id_comment>", methods=["PUT"])
@cross_origin()
def comment_update(id_comment):
    res = request.json
    for key, value in res.items():
        if value == None:
            return jsonify({"msg": "update comment fail", "error": "bad request"}, 401)
        update_status = update_comment(
            id_comment,
            id_user=res.get("id_user"),
            id_book=res.get("id_book"),
            comment=res.get("comment"))
    if update_status:
        return jsonify({"msg": "update comment success", "data": res}, 200)
    else:
        return jsonify({"msg": "no update", "data": None}, 401)


@comment.route("/api/comment/delete/<int:id_comment>", methods=["DELETE"])
@cross_origin()
def comment_delete(id_comment):
    comment = delete_comment(id_comment)
    if comment == None:
        return jsonify({"msg": "no delete", "data": None}, 500)
    else:
        return jsonify({"msg": "delete success", "data": comment}, 200)
