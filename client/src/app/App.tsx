import React from "react";
import {
  Content,
  Header,
  HeaderMenuItem,
  HeaderName,
  HeaderNavigation,
} from "carbon-components-react";
import ConfigurationContent from "../configuration/ConfigurationContent";
import DHCPContent from "../dhcp/DHCPContent";
import MoreInfoContent from "../moreinfo/MoreInfoContent";
import MasquitoLogo from "../resources/mosquito_white.svg";

import "./App.scss";

const App: React.FunctionComponent = () => {
  const [currentPage, setCurrentPage] = React.useState("configuration");

  const ConditionalContent: React.FunctionComponent = () => {
    if (currentPage === "configuration") {
      return <ConfigurationContent />;
    } else if (currentPage === "dhcp") {
      return <DHCPContent />;
    } else if (currentPage === "moreInfo") {
      return <MoreInfoContent />;
    } else {
      return null;
    }
  };

  return (
    <div className="App">
      <Header aria-label="masquito">
        <HeaderName prefix="">
          <img
            className="appLogo"
            src={MasquitoLogo}
            height="80%"
            alt="masquito logo"
          />
          masquito
        </HeaderName>
        <HeaderNavigation aria-label="masquito">
          <HeaderMenuItem
            isCurrentPage={"configuration" === currentPage}
            onClick={() => setCurrentPage("configuration")}
          >
            Configuration
          </HeaderMenuItem>
          <HeaderMenuItem
            isCurrentPage={"dhcp" === currentPage}
            onClick={() => setCurrentPage("dhcp")}
          >
            DHCP
          </HeaderMenuItem>
          <HeaderMenuItem
            isCurrentPage={"moreInfo" === currentPage}
            onClick={() => setCurrentPage("moreInfo")}
          >
            More Info
          </HeaderMenuItem>
        </HeaderNavigation>
      </Header>
      <Content>
        <ConditionalContent />
      </Content>
    </div>
  );
};

export default App;
