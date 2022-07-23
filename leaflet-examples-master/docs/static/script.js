document.addEventListener("DOMContentLoaded", () => {
  const place = document.getElementById("section-example");
  const subTitle = document.querySelector("h2");
  const nav = document.querySelector(".menu-examples");

  async function fetchData(url, type) {
    try {
      const response = await fetch(url);
      const data =
        type === "text" ? await response.text() : await response.json();
      return data;
    } catch (err) {
      console.error(err);
    }
  }

  fetchData("./menu.json", "json").then((data) => {
    data.forEach(({ link, text, info, position }, index) => {
      const element = document.createElement("a");
      element.className = "item";
      if (index == 0) {
        element.classList.add("active-menu");
      }
      element.href = `#${link}`;
      element.setAttribute("data-iframe", link);
      element.setAttribute("data-position", position);
      if (info) {
        element.setAttribute("data-info", info);
      }
      element.textContent = text;
      nav.appendChild(element);
    });

    const examples = document.querySelectorAll(".item");
    examples.forEach((example) => {
      example.addEventListener("click", () => {
        renderIframe(example);
      });
    });

    const hash = location.hash;
    if (hash) {
      renderIframe(hash.replace("#", ""));
    } else {
      renderIframe("01.simple-map");
    }
  });

  function renderIframe(example) {
    // check if object or stirng
    const check = typeof example === "object";

    const activeMenu = document.querySelector(".active-menu");
    activeMenu.classList.remove("active-menu");

    const style = check
      ? example
      : document.querySelector(`a[data-iframe="${example}"`);
    style.classList.add("active-menu");

    const dataIframe = check ? example.getAttribute("data-iframe") : example;

    // --------------------------------------------------
    // set proper position
    const isActive = document.querySelector(".active-menu");
    document.documentElement.removeAttribute("style");
    const positions = isActive.dataset.position.split(",");

    positions.map((position) => {
      position = position.trim().split(":");
      document.documentElement.style.setProperty(position[0], position[1]);
    });
    // --------------------------------------------------

    // h2 title
    const title = document.createElement("h2");
    title.className = "title";
    title.id = dataIframe;
    const createText = document.createTextNode(
      check
        ? example.textContent
        : document.querySelector(`a[data-iframe="${example}"`).textContent
    );

    // adding text to h2
    title.appendChild(createText);

    // create iframe
    const iframe = document.createElement("iframe");
    iframe.src = `./${dataIframe}/index.html`;
    iframe.className = "iframe-wrapper";
    if (dataIframe === "25.fitBounds-with-padding") {
      iframe.classList.add("resize-h");
    }
    iframe.border = 0;
    iframe.width = "100%";
    iframe.height = "550px";

    // get data-info
    const dataInfo = check
      ? example.getAttribute("data-info")
      : document
          .querySelector(`a[data-iframe="${example}"`)
          .getAttribute("data-info");

    const dataInfoTeamplte = dataInfo
      ? `<div class="small">${dataInfo}</div>`
      : "";

    const flex = document.createElement("div");
    flex.className += "flex info-description";

    const fileJS = `${dataIframe}/script.js`;
    const fileCSS = `${dataIframe}/style.css`;
    const template = `
      <div class="small open-source">
        <a href="${detectUrl(fileJS)}" target="_blank">→ open JS file</a> 
        <a href="#" class="full-screen arrow">full screen example</a>
        <a href="#" class="show-code arrow">show JS code</a>
      </div>${dataInfoTeamplte}`;

    flex.innerHTML = template;

    place.innerHTML = "";
    subTitle.innerHTML = "";
    place.insertAdjacentElement("afterbegin", title);
    place.insertAdjacentElement("beforeend", iframe);
    iframe.insertAdjacentElement("afterend", flex);

    const infoDescription = document.querySelector(".info-description");
    const pre = document.createElement("pre");
    pre.className = "code-place hidden";
    const code = document.createElement("code");

    pre.appendChild(code);

    infoDescription.insertAdjacentElement("afterend", pre);

    fetchData(detectUrl(`${fileJS}`), "text")
      .then((data) => {
        code.className = "language-js";
        code.textContent = data;
      })
      .then(() => {
        document.querySelectorAll("pre code").forEach((el) => {
          hljs.highlightElement(el);
        });
      })
      .then(() => {
        const showCode = document.querySelector(".show-code");
        showCode.addEventListener("click", (e) => {
          e.preventDefault();
          pre.classList.toggle("hidden");
          document.body.classList.toggle("show-code-iframe");
        });

        const fullScreen = document.querySelector(".full-screen");
        fullScreen.addEventListener("click", (e) => {
          e.preventDefault();
          document.body.classList.add("show-code-full-screen");
        });

        const closeFullScreen = document.querySelector(".hide-iframe");
        closeFullScreen.addEventListener("click", (e) => {
          e.preventDefault();
          document.body.classList.remove("show-code-full-screen");
        });
      });

    document.body.classList.remove("show-menu-examples");
  }

  function detectUrl(file) {
    let url =
      location.hostname === "localhost" || location.hostname === "127.0.0.1"
        ? file
        : `https://raw.githubusercontent.com/tomik23/leaflet-examples/master/docs/${file}`;
    return url;
  }
});

// ------------------------------------------------------------
// scroll shadow
const menu = document.querySelector(".menu-examples");

menu.addEventListener("scroll", addShadow);

function addShadow(e) {
  const el = e.target;
  if (el.scrollTop >= 52) {
    addRemoveClass(el, "add", "center-shadow");
    addRemoveClass(el, "remove", "top-shadow");
  } else {
    addRemoveClass(el, "add", "top-shadow");
    addRemoveClass(el, "remove", "center-shadow");
  }
}

function addRemoveClass(el, type, className) {
  return el.classList[type](className);
}

// ------------------------------------------------------------
// show menu

const showMenu = document.querySelector(".show-menu");
showMenu.addEventListener("click", (e) => {
  e.preventDefault();
  document.body.classList.toggle("show-menu-examples");
});

// close when click esc
window.addEventListener("keydown", function (event) {
  // close sidebar when press esc
  if (event.key === "Escape") {
    document.body.classList.remove("show-code-full-screen");
  }
});
