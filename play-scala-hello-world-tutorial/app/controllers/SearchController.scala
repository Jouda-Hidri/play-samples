package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class SearchController @Inject()(val controllerComponents: ControllerComponents)
    extends BaseController {

def showForm = Action { implicit request: Request[AnyContent] =>
  Ok(views.html.searchForm(request))
}


  def submitSearch = Action { implicit request =>
    val searchTerm = request.body.asFormUrlEncoded
      .flatMap(_.get("query").flatMap(_.headOption))
    Ok(s"You searched for: ${searchTerm.getOrElse("")}")
  }
}
