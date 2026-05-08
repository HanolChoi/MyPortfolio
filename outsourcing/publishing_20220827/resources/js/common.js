$(document).ready(function () {
  $(".selected").click(function (e) {
    e.preventDefault();
    $(".lang").toggleClass("on");
    $(this).siblings(".lists").slideToggle("fast");
  });

  $("#menu-btn").click(function (e) {
    e.preventDefault();
    $("html").toggleClass("on");
    $("#header").toggleClass("on");
    if ($("#header").hasClass("on")) {
      $("#header .sns ul").fadeIn();
    } else {
      $("#header .sns ul").fadeOut();
    }
  });

  $(".more").click(function () {
    $(this).hide();
    $(".p-box").addClass("on");
  });

  $(".faq-lists a").click(function (e) {
    e.preventDefault();
    $(this).parents("li").find(".faq-content").slideToggle("fast");
    $(this).parents("li").toggleClass("on");
  });

  function headerEventForPC() {
    $("html").removeClass("on");
    $("#header").removeClass("on");
    $("#header .sns ul").removeAttr("style");
  }

  var isTrue = true;
  function animateHeader() {
    if ($("body").width() > 1023 && isTrue) {
      isTrue = false;
      headerEventForPC();
    }
    if ($("body").width() <= 1023 && !isTrue) {
      isTrue = true;
    }
  }

  if ($("body").width() > 1023) {
    headerEventForPC();
  }

  function scrollAnimation() {
    $(".ani, .ani1, .ani2").each(function () {
      var thisPoint = $(this).offset().top;
      var aniPoint = thisPoint + $(window).height() / 7;
      var windowBottom = $(window).scrollTop() + $(window).height();

      if (windowBottom > aniPoint) {
        if (!$(this).hasClass("active")) {
          $(this).addClass("active");
        }
      }

      if (windowBottom < thisPoint) {
        if ($(this).hasClass("active")) {
          $(this).removeClass("active");
        }
      }
    });
  }
  scrollAnimation();

  $(window).scroll(function () {
    scrollAnimation();
  });

  $(window).resize(function () {
    animateHeader();
    scrollAnimation();
  });
});
