var angular = require("angular");
var $ = require("jquery");
//hide and show container.
$(document).ready(function() {
  $("#message").hide();

  $("#load_label_tab").click(function(e) {
    $("#load_label")
      .delay(100)
      .fadeIn(100);
    $("#search_label").fadeOut(100);
    $(this).addClass("active");
    e.preventDefault();
  });

  $("#search_label_tab").click(function(e) {
    $("#search_label")
      .delay(100)
      .fadeIn(100);
    $("#load_label").fadeOut(100);
    $(this).addClass("active");
    e.preventDefault();
  });
});

var baseURL = "http://localhost:8082/api/translate";

// Angular app module.
var app = angular.module("app", []);

var translations = [];

app.run(function($rootScope, $http) {
  var payload = [
    {
      language: "English",
      value: "Send"
    },
    {
      language: "German",
      value: "Senden"
    }
  ];
  $rootScope.samplePayload = JSON.stringify(payload);
});

app.directive("fileModel", [
  "$parse",
  function($parse) {
    return {
      restrict: "A",
      link: function(scope, element, attrs) {
        var model = $parse(attrs.fileModel);
        var modelSetter = model.assign;

        element.bind("change", function() {
          scope.$apply(function() {
            modelSetter(scope, element[0].files[0]);
          });
        });
      }
    };
  }
]);

app.service("fileUpload", [
  "$http",
  function($http) {
    this.uploadFileToUrl = function(file, uploadUrl) {
      console.log(file);
      var fd = new FormData();
      fd.append("file", file);
      console.log(baseURL + uploadUrl);
      $http
        .post(baseURL + uploadUrl, fd, {
          transformRequest: angular.identity,
          headers: { "Content-Type": undefined }
        })
        .then(function(response) {
          console.log(response);
          alert(
            "Status : " +
              response.data.status +
              ", records processed: " +
              response.data.recordsProcessed
          );
        })
        .catch(function(response) {
          console.log(response);
          alert("Status: " + response.data.error.message);
        });
    };

    this.postLabels = function(label, payload, url) {
      $http
        .post(baseURL + url + "?label=" + label, payload, {
          headers: { "Content-Type": "application/json" }
        })
        .then(function(response) {
          console.log(response);
          alert(
            "Status : " +
              response.data.status +
              ", records processed: " +
              response.data.recordsProcessed
          );
        })
        .catch(function(response) {
          console.log(response);
          alert("Status: " + response.data.error.message);
        });
    };
  }
]);

app.controller("translationsController", [
  "$scope",
  "$http",
  "fileUpload",
  function($scope, $http, fileUpload) {
    var translationList = [];
    $scope.uploadFile = function() {
      var file = $scope.myFile;
      var uploadUrl = "/load";
      fileUpload.uploadFileToUrl(file, uploadUrl);
    };

    $scope.postLabels = function() {
      var payload = $scope.jsonPayload;
      var label = $scope.label;
      fileUpload.postLabels(label, payload, "/load");
    };

    $scope.search = function() {
      var label = $scope.label;
      var language = $scope.language;
      var searchUrl = baseURL + "/search";
      if (language === undefined || language == null || language.length <= 0) {
        searchUrl = searchUrl + "?label=" + label;
      } else {
        searchUrl = searchUrl + "?label=" + label + "&language=" + language;
      }
      $http
        .get(searchUrl)
        .then(function(response) {
          console.log(
            "LabelDocument: " + response.data.labelDocument.translations
          );
          $scope.translations = response.data.labelDocument.translations;
        })
        .catch(function(response) {
          console.log(response);
          alert("Status: " + response.data.error.message);
        });
    };
  }
]);
